package no.api.pushbullet;

import com.google.gson.Gson;
import no.api.pushbullet.items.chat.Chat;
import no.api.pushbullet.items.chat.Chats;
import no.api.pushbullet.items.device.Device;
import no.api.pushbullet.items.device.Devices;
import no.api.pushbullet.items.ephemeral.*;
import no.api.pushbullet.items.push.FileUploadRequest;
import no.api.pushbullet.items.push.Push;
import no.api.pushbullet.items.push.Pushes;
import no.api.pushbullet.items.subscription.ChannelInfo;
import no.api.pushbullet.items.subscription.Subscription;
import no.api.pushbullet.items.subscription.Subscriptions;
import no.api.pushbullet.items.user.User;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Controls all connection to the Pushbullet API. Contains all methods to access and send data.
 *
 * @author silk8192
 */
public class PushbulletClient {

    /**
     * Our http client
     */
    private CloseableHttpClient client;
    /**
     * Used for parsing resulting data from the API in JSON
     */
    private Gson gson;
    private String URL = "https://api.pushbullet.com/v2";
    private Logger logger = LoggerFactory.getLogger(PushbulletClient.class);

    private List<Device> devices;

    private User currentUser;

    /**
     * Create instances of the http client and other needed things. Also specifies what logging level to use.
     *
     * @param api_key The only credential to be passed. Acts as user/password.
     */
    public PushbulletClient(String api_key) {
        // Carries the api key that verifies with the API
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(new AuthScope("api.pushbullet.com", 443), new UsernamePasswordCredentials(api_key, null));
        this.client = HttpClients.custom().setDefaultCredentialsProvider(credsProvider).build();
        this.gson = new Gson();
        this.devices = this.listDevices();
        this.currentUser = this.updateCurrentUser();
    }

    public List<Device> listDevices() {
        return gson.fromJson(get(URL + "/devices"), Devices.class).getDevices();
    }

    public Device createDevice(String nickname, String model, String manufacturer, String appVersion, String icon, String hasSms) {
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("active", "true"));
        nameValuePairs.add(new BasicNameValuePair("app_version", appVersion));
        nameValuePairs.add(new BasicNameValuePair("push_token", ""));
        nameValuePairs.add(new BasicNameValuePair("manufacturer", manufacturer));
        nameValuePairs.add(new BasicNameValuePair("model", model));
        nameValuePairs.add(new BasicNameValuePair("has_sms", String.valueOf(hasSms)));
        nameValuePairs.add(new BasicNameValuePair("icon", icon));
        nameValuePairs.add(new BasicNameValuePair("nickname", icon));
        return gson.fromJson(post(URL + "/devices", nameValuePairs), Device.class);
    }

    public void deleteDevice(String iden) {
        delete(URL + "/devices/" + iden);
    }

    public List<Chat> getChats() {
        return gson.fromJson(get(URL + "/chats"), Chats.class).getChats();
    }

    public Chat createChat(String email) {
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("email", email));
        return gson.fromJson(post(URL + "/chats", nameValuePairs), Chat.class);
    }

    public Chat updateChat(String iden, boolean muted) {
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("muted", String.valueOf(muted)));
        return gson.fromJson(post(URL + "/chats/" + iden, nameValuePairs), Chat.class);
    }

    public void deleteChat(String iden) {
        delete(URL + "/chats/" + iden);
    }

    public List<Subscription> listSubscriptions() {
        return gson.fromJson(get(URL + "/subscriptions"), Subscriptions.class).getSubscriptions();
    }

    public Subscription createSubscription(String channel_tag) {
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("channel_tag", channel_tag));
        return gson.fromJson(post(URL + "subscriptions", nameValuePairs), Subscription.class);
    }

    public Subscription updateSubscription(String iden, boolean muted) {
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("muted", String.valueOf(muted)));
        return gson.fromJson(post(URL + "/subscriptions/" + iden, nameValuePairs), Subscription.class);
    }

    public ChannelInfo channelInfo(String channelTag, boolean noRecentPushes) {
        try {
            ChannelInfo channelInfo = gson.fromJson(get(URL + "/channel-info?tag="
                    + URLEncoder.encode(channelTag, "UTF-8")
                    + "&no_recent_pushes=" + URLEncoder.encode(String.valueOf(noRecentPushes), "UTF-8")), ChannelInfo.class);
            if (channelInfo.getIden() == null) {
                logger.error("Could not request channel info for given tag: " + channelTag);
                return null;
            }
            return channelInfo;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteSubscription(String iden) {
        delete(URL + "/subscriptions/" + iden);
    }

    public User updateCurrentUser() {
        return gson.fromJson(get(URL + "/users/me"), User.class);
    }

    public User getCurrentUser() {
        return this.currentUser;
    }

    public String getCurrentUserIden() {
        return this.currentUser.getIden();
    }

    public List<Push> listPushes(String modifiedAfter, String active, String cursor, int limit) {
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("modified_after", modifiedAfter));
        nameValuePairs.add(new BasicNameValuePair("active", active));
        nameValuePairs.add(new BasicNameValuePair("cursor", cursor));
        nameValuePairs.add(new BasicNameValuePair("limit", Integer.toString(limit)));
        return gson.fromJson(post(URL + "/pushes", nameValuePairs), Pushes.class).getPushes();
    }

    public Push sendNotePush(String title, String body) {
        try {
            return sendPush("note", title, body, "", "", "", "", "", "", "", "", "", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Push sendLinkPush(String title, String body, String link) {
        try {
            return sendPush("link", title, body, link, "", "", "", "", "", "", "", "", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Push sendPush(String type, String title, String body, String url,
                         String file_name, String file_type, String file_url,
                         String source_device_iden, String device_iden,
                         String client_iden, String channel_tag, String email, String guid) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        if (type.equals("link")) {
            nameValuePairs.add(new BasicNameValuePair("title", title));
            nameValuePairs.add(new BasicNameValuePair("body", body));
            nameValuePairs.add(new BasicNameValuePair("url", url));
            return gson.fromJson(post(URL + "/pushes", nameValuePairs), Push.class);
        } else if (type.equals("note")) {
            nameValuePairs.add(new BasicNameValuePair("type", "note"));
            nameValuePairs.add(new BasicNameValuePair("title", title));
            nameValuePairs.add(new BasicNameValuePair("body", body));
            return gson.fromJson(post(URL + "/pushes", nameValuePairs), Push.class);
        } else {
            throw new Exception("Invalid push type!");
        }
    }

    public void sendFilePush(String body, String fileName, String fileType, File file) {
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("file_name", fileName));
        nameValuePairs.add(new BasicNameValuePair("file_type", fileType));
        FileUploadRequest request = gson.fromJson(post(URL + "/upload-request", nameValuePairs), FileUploadRequest.class);
        System.out.println(request.getFileUrl());
        postFile(request.getUploadUrl(), file);
    }

    private void postFile(String fileUploadUrl, File file) {
        if (file.length() >= 26214400) {
            logger.error("The file you are trying to upload is too big. File: {}, Size: {}", file.getName(), file.length());
        }

        HttpPost post = new HttpPost(fileUploadUrl);
        HttpResponse response = null;
        try {
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addBinaryBody("file", file);
            post.setEntity(builder.build());

            response = client.execute(post);
            logger.info("Type: {}", response.getStatusLine());
        } catch (IOException e) {
            logger.error("While posting file", e);
        }
    }

    public Push updatePush(String iden, boolean dismissed) {
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("dismissed", String.valueOf(dismissed)));
        return gson.fromJson(post(URL + "/pushes/" + iden, nameValuePairs), Push.class);
    }

    public void deletePush(String iden) {
        delete(URL + "/pushes/" + iden);
    }

    public void deleteAllPushes() {
        delete(URL + "/pushes");
    }

    public void sendNotificationPush(String title, String body, String applicationName, int clientVersion, boolean dismissible, boolean hasRoot, String icon, String notificationId, String packageName, String sourceDeviceIden) {
        NotificationPush push = new NotificationPush();
        push.setPush(new NotificationData());
        push.setType("push");
        push.getPush().setType("mirror");
        push.getPush().setIcon(icon);
        push.getPush().setClientVersion(clientVersion);
        push.getPush().setSourceUserIden(this.getCurrentUserIden());
        push.getPush().setSourceDeviceIden(sourceDeviceIden);
        push.getPush().setBody(body);
        push.getPush().setDismissable(dismissible);
        push.getPush().setNotificationId(notificationId);
        push.getPush().setNotificationTag(null);
        push.getPush().setApplicationName(applicationName);
        push.getPush().setHasRoot(hasRoot);
        push.getPush().setPackageName(packageName);
        push.getPush().setTitle(title);
        System.out.println(gson.toJson(push, NotificationPush.class));
        System.out.println(post(URL + "/ephemerals", gson.toJson(push, NotificationPush.class)));
    }

    public void sendSMSPush(String convIden, String message, String packageName, String target) {
        SMSPush push = new SMSPush();
        push.setPush(new SMSData());
        push.getPush().setConversationIden(convIden);
        push.getPush().setMessage(message);
        push.getPush().setPackageName(packageName);
        push.getPush().setSourceUserIden(this.getCurrentUserIden());
        push.getPush().setTargetDeviceIden(target);
        push.getPush().setType("messaging_extension_reply");
        push.setType("push");
        System.out.println(post(URL + "/ephemerals", gson.toJson(push, SMSPush.class)));
    }

    public void sendClipboardPush(String body) {
        ClipboardPush push = new ClipboardPush();
        push.setType("push");
        push.setPush(new ClipboardData());
        push.getPush().setBody(body);
        push.getPush().setSourceUserIden(this.getCurrentUserIden());
        push.getPush().setType("clip");
    }

    private String post(String path, String json) {
        HttpPost post = new HttpPost(path);
        String result = null;
        try {
            post.setEntity(new StringEntity(json));
            post.setHeader("Accept", "application/json");
            post.setHeader("Content-type", "application/json");
            HttpResponse response = client.execute(post);

            logger.info(response.getStatusLine().toString());
            result = collectResponse(response);
        } catch (IOException e) {
            logger.error("Error sending post request", e);
        }
        return result;
    }

    private String post(String path, List<NameValuePair> nameValuePairs) {
        HttpPost post = new HttpPost(path);
        String result = null;
        try {
            post.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
            HttpResponse response = client.execute(post);

            logger.info("Type: {}", response.getStatusLine());
            result = collectResponse(response);
        } catch (IOException e) {
            logger.error("While posting", e);
        }
        return result;
    }

    private String collectResponse(HttpResponse response) {
        StringBuilder result = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))) {
            for (String line; (line = br.readLine()) != null; ) {
                result.append(line);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    private String get(String path) {
        HttpGet get = new HttpGet(path);
        String result = null;
        try (CloseableHttpResponse response = client.execute(get)) {
            logger.info("Type: {}", response.getStatusLine());
            result = collectResponse(response);
        } catch (ClientProtocolException e) {
            logger.error("While getting", e);
        } catch (IOException e) {
            logger.error("While getting", e);
        }
        return result;
    }

    private String delete(String path) {
        HttpDelete delete = new HttpDelete(path);
        String result = null;
        try {
            HttpResponse response = client.execute(delete);
            logger.info("Type: {}", response.getStatusLine());
            result = collectResponse(response);
        } catch (IOException e) {
            logger.error("While getting", e);
        }
        return result;
    }

    public Device getDevice(int id) {
        return this.devices.get(id);
    }

    public Device getDeviceByIden(String iden) {
        Optional<Device> device = this.devices.stream().filter(d -> d.getIden().equals(iden)).filter(Objects::nonNull).findFirst();
        return device.get();
    }

    public List<Device> getDevices() {
        return devices.stream().filter(d -> d.getActive()).collect(Collectors.toList());
    }

}
