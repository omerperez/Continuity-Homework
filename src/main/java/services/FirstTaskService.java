package services;

import Models.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static constant.IConstantFirstPart.Paths.*;

public class FirstTaskService {

    private static FirstTaskService instance;
    private FirstTaskService() {}
    public synchronized static FirstTaskService getInstance(){
        if(instance == null){
            instance = new FirstTaskService();
        }
        return instance;
    }

    public Gson createGsonBuilder(){
        return new GsonBuilder().disableHtmlEscaping().create();
    }

    public Boolean isResponseNotEmpty(String response){
        if(response != null && !(response.isEmpty() || response.equals("[]"))){
            return true;
        } else {
            Logger.getLogger("services").info(String.format("Response from Api is empty"));
            return false;
        }
    }

    public String getResponse(String api) {
        Logger.getLogger("services").info("Send Get Request to" + api);
        return HttpRequest.getInstance().sendGetRequest(api);
    }

    public List<Integer> getUsersIdList(){
        String usersApi = baseUrl + users;
        try {
            String response = getResponse(usersApi);
            if(isResponseNotEmpty(response)){
                Gson gsonBuilder = createGsonBuilder();
                Type listOfUsersObject = new TypeToken<ArrayList<User>>() {}.getType();
                List<User> users = gsonBuilder.fromJson(response, listOfUsersObject);
                return users.stream().map(User::getId).collect(Collectors.toList());
            }
        } catch (Exception e){
            System.out.println(e);
            Logger.getLogger("services").warning(e.toString());
        }
        return null;
    }

    public Collection getUncompletedTask(){
        String uncompletedTaskApi = String.format("%s%s%s",baseUrl, todos, uncompletedTask);
        Map<Integer, List<Todo>> map = null;
        try {
            String response = getResponse(uncompletedTaskApi);
            if(isResponseNotEmpty(response)){
                Gson gsonBuilder = createGsonBuilder();
                Type listOfTodosObject = new TypeToken<ArrayList<Todo>>() {}.getType();
                List<Todo> uncompletedTodos = gsonBuilder.fromJson(response, listOfTodosObject);
                map = uncompletedTodos.stream().collect(
                        Collectors.groupingBy(Todo::getUserId)
                );
            }
        } catch (Exception e){
            System.out.println(e);
            Logger.getLogger("services").warning(e.toString());
        }
        return map == null ? null : map.entrySet();
    }

    public Collection getUncompletedTasksByUserId(Integer userId){
        String uncompletedTodosByUserIdApi = String.format(
                "%s%s?completed=false&&userId=%s",
                baseUrl,
                todos,
                userId
        );
        try {
            String response = getResponse(uncompletedTodosByUserIdApi);
            if(isResponseNotEmpty(response)){
                Gson gsonBuilder = createGsonBuilder();
                Type listOfTodosObject = new TypeToken<ArrayList<Todo>>() {}.getType();
                return gsonBuilder.fromJson(response, listOfTodosObject);
            }
        } catch (Exception e){
            System.out.println(e);
            Logger.getLogger("services").warning(e.toString());
        }
        return null;
    }

    public Map<Integer, List<String>>  getActivePostIdList(){
        String commentsApi = String.format("%s%s", baseUrl, comments);
        try {
            String response = getResponse(commentsApi);
            if(isResponseNotEmpty(response)){
                Gson gsonBuilder = createGsonBuilder();
                Type listOfCommentsObject = new TypeToken<ArrayList<Comment>>() {}.getType();
                List<Comment> comments = gsonBuilder.fromJson(response, listOfCommentsObject);
                return comments.stream().collect(
                        Collectors.groupingBy(x -> x.getPostId(), Collectors.mapping(x -> x.getEmail(), Collectors.toList()))
                );
            }
        } catch(Exception e){
            System.out.println(e);
            Logger.getLogger("services").warning(e.toString());
        }
        return null;
    }

    public Collection getActivePostsSummary(){
        String postsApi = String.format("%s%s", baseUrl, posts);
        Map<Integer, List<String>> activePostsSummary = new HashMap<>();
        try {
            String response = getResponse(postsApi);
            if(isResponseNotEmpty(response)) {
                Gson gsonBuilder = createGsonBuilder();
                Type listOfPostsObject = new TypeToken<ArrayList<Post>>() {
                }.getType();
                List<Post> posts = gsonBuilder.fromJson(response, listOfPostsObject);

                Map<Integer, List<Integer>> postsPerUserId = posts.stream().collect(
                        Collectors.groupingBy(Post::getUserId,
                                Collectors.mapping(Post::getId, Collectors.toList())
                        )
                );

                Map<Integer, List<String>> activePostIdEmailsMap = getActivePostIdList();

                for (Integer userId : postsPerUserId.keySet()) {
                    List<String> activePosts = new ArrayList<>();
                    for (Integer postId : postsPerUserId.get(userId)) {
                        if (activePostIdEmailsMap.containsKey(postId)) {
                            activePosts.add(
                                    String.format("Post ID - %s, Emails repliers: %s \n",
                                            postId,
                                            activePostIdEmailsMap.get(postId).toString()));
                        }
                    }
                    activePostsSummary.put(userId, activePosts);
                }
                return activePostsSummary.entrySet();
            }
        } catch (Exception e) {
            System.out.println(e);
            Logger.getLogger("services").warning(e.toString());
        }
        return null;
    }

    public Boolean isUserExist(Integer userId){
        String usersByIdApi = String.format("%s%s/%s", baseUrl, users, userId);
        try {
            String response = getResponse(usersByIdApi);
            Gson gsonBuilder = createGsonBuilder();
            User currentUser = gsonBuilder.fromJson(response, User.class);
            return currentUser != null;
        } catch (Exception e){
            System.out.println(e);
            Logger.getLogger("services").warning(e.toString());
        }
        return false;
    }

    public String listToParams(List<Integer> list){
        String params = "";
        for(int i =0; i < list.size(); i++){
            if(i == 0){
                params += String.format("?albumId=%s", list.get(i));
            } else {
                params += String.format("&&albumId=%s", list.get(i));
            }
        }
        return String.format("%s%s%s" ,baseUrl, photos, params);
    }

    public Set<Integer> getAlbumsKeysAboveThreshold(Map<Integer, List<Integer>> albumsIdImages, Integer threshold){
        if(!(albumsIdImages == null || albumsIdImages.isEmpty())){
            return albumsIdImages.entrySet().stream().filter(
                            x-> x.getValue().size() >= threshold)
                    .collect(Collectors.toMap(
                                    Map.Entry:: getKey, Map.Entry::getValue
                            )
                    ).keySet();
        }
        return null;
    }

    public Set<Integer> getAlbumsIdAboveThreshold(String params, Integer threshold){
        try{
            String response = getResponse(params);
            Gson gsonBuilder = createGsonBuilder();
            Type listOfPhotosObject = new TypeToken<ArrayList<Photo>>() {}.getType();
            List<Photo> photos = gsonBuilder.fromJson(response, listOfPhotosObject);

            Map<Integer, List<Integer>> albumsIdImages = photos.stream().collect(
                    Collectors.groupingBy(
                            x -> x.getAlbumId(),
                            Collectors.mapping(
                                    x -> x.getId(),
                                    Collectors.toList()
                            )
                    )
            );
            return getAlbumsKeysAboveThreshold(albumsIdImages, threshold);
        } catch (Exception e){
            System.out.println(e);
            Logger.getLogger("services").warning(e.toString());
        }
        return null;
    }

    public Collection getAlbumsByUserId(Integer userId, Integer photosThreshold){
        if(isUserExist(userId)){
            String postsApi = String.format("%s%s?userId=%s", baseUrl, albums, userId);
            try {
                String response = getResponse(postsApi);
                Gson gsonBuilder = createGsonBuilder();
                Type listOfAlbumsObject = new TypeToken<ArrayList<Album>>() {}.getType();
                List<Album> userAlbums = gsonBuilder.fromJson(response, listOfAlbumsObject);

                List<Integer> albumsId = userAlbums.stream()
                        .map(Album:: getId).collect(Collectors.toList());
                if(!albumsId.isEmpty()) {
                    String params = listToParams(albumsId);
                    return getAlbumsIdAboveThreshold(params, photosThreshold);
                }
            } catch (Exception e){
                System.out.println(e);
                Logger.getLogger("services").warning(e.toString());
            }
        }
        return null;
    }
}
