import services.Functions;

import java.util.Collection;
import java.util.List;

public class MainTrainTask1 {

    public static void main(String[] args) {
		Functions functions = Functions.getInstance();
		System.out.println("********** Get All Uncompleted Tasks Per User **********");
		Collection<?> allUsersUncompletedTasksResults = functions.getUncompletedTaskPerUserId();
		System.out.println(allUsersUncompletedTasksResults);

		System.out.println("********** Get Uncompleted Tasks For User ID - 1 **********");
		Collection<?> specificUserUncompletedTasksResults = functions.getUserUncompletedTasks("1");
		System.out.println(specificUserUncompletedTasksResults);

		System.out.println("********** Get Active Posts Summary **********");
		Collection<?> postsSummary = functions.getPostsSummary();
		System.out.println(postsSummary);

		System.out.println("********** Get All Albums For User ID 1 That Includes More Than 50 images **********");
		List<Integer> c = functions.getAlbumsByUserId("1", 50);
		System.out.println(c.toString());
    }
}
