package services;

import Data.ActivePost;
import Data.Task;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;
import java.util.stream.Collectors;

import static constant.IConstant.Paths.*;

public class Functions {
    private static Functions instance;

    private Functions() {}

    public synchronized static Functions getInstance(){
        if(instance == null){
            instance = new Functions();
        }
        return instance;
    }

    public JSONArray getJsonArrayResponseFromApi(String api) throws JSONException {
        String response = HttpRequest.getInstance().sendGetRequest(api);
        String[] s = response.split("}");
        Object[] arr = new Object[s.length];
        return new JSONArray(response);
    }

    public String getStringValueOfObjectKey(JSONObject object, String objectKey) throws JSONException {
        return object.getString(objectKey);
    }

    public Collection<?> getUncompletedTaskPerUserId(){
        String allUncompletedTasksApi = baseUrl + todos + uncompletedTask;
        Map<String, List<Task>> map = new HashMap<>();
        try {
            JSONArray response = getJsonArrayResponseFromApi(allUncompletedTasksApi);
            for (int i = 0; i < response.length(); i++) {
                List<Task> taskArray;
                JSONObject object = response.getJSONObject(i);
                String userId = object.getString("userId");
                if(map.containsKey(userId)){
                    taskArray = map.get(userId);
                } else {
                    taskArray = new ArrayList<>();
                }
                taskArray.add(
                        new Task(
                                object.getInt("id"),
                                object.getString("title"),
                                object.getBoolean("completed")
                        )
                );
                map.put(userId, taskArray);
            }
        } catch (Exception e){
            System.out.println(e);
        }
        return Collections.singleton(map);
    }

    public Collection<?> getUserUncompletedTasks(String userId){
        String uncompletedTasksByUserIdApi = baseUrl + todos + "?completed=false" + "&&userId=" + userId;
        Map<String, List<Task>> map = new HashMap<>();
        List<Task> uncompletedTasks = new ArrayList<>();
        try {
            JSONArray jsonResponse = getJsonArrayResponseFromApi(uncompletedTasksByUserIdApi);
            for (int i = 0; i < jsonResponse.length(); i++) {
                JSONObject object = jsonResponse.getJSONObject(i);
                uncompletedTasks.add(
                        new Task(
                                Integer.parseInt(object.get("id").toString()),
                                object.get("title").toString(),
                                object.get("completed").equals("true")
                        )
                );
            }
            map.put(userId, uncompletedTasks);
        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }
        return Collections.singleton(map.get(userId));
    }

    public Map<String, List<String>> getCommentsData(){
        String commentApi = baseUrl + comments;
        Map<String, List<String>> comments = new HashMap<>();
        try {
            JSONArray commentResponse = getJsonArrayResponseFromApi(commentApi);
            for (int i = 0; i < commentResponse.length(); i++) {
                List<String> postComments;
                JSONObject object = commentResponse.getJSONObject(i);
                String postId = object.getString("postId");
                if(comments.containsKey(postId)){
                    postComments = comments.get(postId);
                } else {
                    postComments = new ArrayList<>();
                }
                postComments.add(
                        object.getString("email")
                );
                comments.put(postId, postComments);
            }
        } catch (JSONException exception){
            System.out.println(exception);
        }
        return comments;
    }

    public List<Integer> getUserAlbumsAndInitParams(JSONArray response, StringBuilder params) throws JSONException {
        List<Integer> albums = new ArrayList<>();
        for(int i = 0; i < response.length(); i++){
            Integer currentAlbumId = response.getJSONObject(i).getInt("id");
            String albumIdParams = "albumId=";
            if( i == 0){
                params.append(
                        String.format(
                                "?%s%s",
                                albumIdParams,
                                currentAlbumId
                        )
                );
            } else{
                params.append(
                        String.format("&&%s%s",
                                albumIdParams,
                                currentAlbumId
                        )
                );
            }
            albums.add(currentAlbumId);
        }
        return albums;
    }

    public Map<Integer, Integer> getImagesCountPerAlbum(JSONArray imagesResponse) throws JSONException {
        Map<Integer, Integer> albumImageCount = new HashMap<>();
        for (int i = 0; i < imagesResponse.length(); i++) {
            Integer albumId = imagesResponse.getJSONObject(i).getInt("albumId");
            if (albumImageCount.containsKey(albumId)) {
                albumImageCount.put(albumId, albumImageCount.get(albumId) + 1);
            } else {
                albumImageCount.put(albumId, 1);
            }
        }
        return albumImageCount;
    }

    public List<Integer> getAlbumsByUserId(String userId, Integer photosThreshold){
        String albumsApi =  String.format("%s%s?userId=%s", baseUrl, albums, userId);
        String photosApi = baseUrl.concat(photos);
        try{
            StringBuilder params = new StringBuilder();
            JSONArray albumsResponse = getJsonArrayResponseFromApi(albumsApi);
            List<Integer> albumsId = getUserAlbumsAndInitParams(albumsResponse, params);
            JSONArray imagesResponse = getJsonArrayResponseFromApi(photosApi.concat(params.toString()));
            Map<Integer, Integer> imagesCountPerAlbum = getImagesCountPerAlbum(imagesResponse);
            if(albumsId.size() > 0) {
                return albumsId.stream().filter(
                        currentAlbumId ->
                                photosThreshold <= imagesCountPerAlbum.get(currentAlbumId)
                ).collect(Collectors.toList());
            }
        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }
        return null;
    }

    public Collection<?> getPostsSummary(){
        String allPostsApi = baseUrl.concat(posts);
        Map<String, List<ActivePost>> postsSummary = new HashMap<>();
        try {
            Map<String, List<String>> postsComments = getCommentsData();
            JSONArray response = getJsonArrayResponseFromApi(allPostsApi);
            for (int i = 0; i < response.length(); i++) {
                JSONObject object = response.getJSONObject(i);
                String postId = object.getString("id");
                if(postsComments.containsKey(postId)) {
                    List<ActivePost> userPosts;
                    String userId = object.getString("userId");
                    if (postsSummary.containsKey(userId)) {
                        userPosts = postsSummary.get(userId);
                    } else {
                        userPosts = new ArrayList<>();
                    }
                    userPosts.add(
                            new ActivePost(
                                    Integer.parseInt(postId),
                                    object.getString("title"),
                                    postsComments.get(postId)
                            )
                    );
                    postsSummary.put(userId, userPosts);
                }
            }
        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }
        return Collections.singleton(postsSummary);
    }
}
