import services.FirstTaskService;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class MainTrainTask1 {

	public static void main(String[] args) throws IOException {

		FileHandler handler = new FileHandler("FirstTaskLogs.log", true);
		Logger logger = Logger.getLogger("services");
		logger.addHandler(handler);
		SimpleFormatter formatter = new SimpleFormatter();
		handler.setFormatter(formatter);


		/* Get All Users From Api */
		List<Integer> usersId = FirstTaskService.getInstance().getUsersIdList();
		if(usersId != null){
			/* Method 1 */
			logger.info("* * * Get All Uncompleted Tasks From todos api * * *");
			Collection uncompletedTasks = FirstTaskService.getInstance().getUncompletedTask();
			logger.info(String.format("* * * Uncompleted Todos From Api : %s", uncompletedTasks));

			/* Method 2 */
			for(Integer userId : usersId) {
				logger.info(String.format("Get All Uncompleted Tasks For User: %s", userId));
				Collection uncompletedTasksByUserId = FirstTaskService.getInstance().getUncompletedTasksByUserId(userId);
				logger.info(String.format("* * * Uncompleted Task for User ID - %s: %s", userId, uncompletedTasksByUserId));
			}

			/* Method 3 */
			logger.info("Get active posts summary for each user");
			Collection activePosts = FirstTaskService.getInstance().getActivePostsSummary();
			logger.info(String.format("Active posts summary: \n %s", activePosts));

			logger.info("All albums of a specific user that contains more photos than a given threshold");
			for(Integer userId: usersId){
				Random r = new Random();
				Integer randomThreshold = r.nextInt(200);
				Collection albums = FirstTaskService.getInstance().getAlbumsByUserId(userId, randomThreshold);
				logger.info(String.format("All albums of User ID - %s that contains more photos than %s: %s", userId, randomThreshold, albums));
			}
			logger.info("Finished Task 1");
		} else {
			System.out.println("No users found");
		}





//		System.out.println("* * * Get Active Posts Summary * * *");
//		Collection<?> postsSummary = functions.getPostsSummary();
//		System.out.println(postsSummary);
//
//		System.out.println("* * * Get All Users Id * * *");
//		List<Integer> idList = functions.getUsersIdList();
//		System.out.println(idList.toString());
//
//		Random random = new Random();
//		for(int i = 0; i < idList.size(); i++){
//			String uncompletedTaskTitle = String.format("* * * Get Uncompleted Tasks For User ID - %s * * *", i + 1);
//			System.out.println(uncompletedTaskTitle);
//			Collection<?> specificUserUncompletedTasksResults = functions.getUserUncompletedTasks(i + 1);
//			System.out.println(specificUserUncompletedTasksResults);
//
//			Integer threshold = random.nextInt(100);
//			String albumTitle = String.format("* * * Get All Albums For User ID %s That Includes More Than %s images * * *", i + 1, threshold);
//			System.out.println(albumTitle);
//			List<Integer> c = functions.getAlbumsByUserId(i + 1, threshold);
//			if(c == null) {
//				System.out.println("No Albums");
//			} else {
//				System.out.println(c);
//			}
//		}
    }
}
