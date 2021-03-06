package mn.aug.restfulandroid.service;

import java.util.List;

import android.content.Context;
import mn.aug.restfulandroid.rest.RestMethod;
import mn.aug.restfulandroid.rest.RestMethodFactory;
import mn.aug.restfulandroid.rest.RestMethodResult;
import mn.aug.restfulandroid.rest.RestMethodFactory.Method;
import mn.aug.restfulandroid.rest.resource.Profile;
import mn.aug.restfulandroid.rest.resource.Tweet;
import mn.aug.restfulandroid.rest.resource.TwitterTimeline;


import android.content.Context;

/**
 * The TimelineProcessor is a POJO for processing timeline requests.
 * For this pattern, there is one Processor for each resource type.
 * 
 * @author Peter Pascale
 */
public class TimelineProcessor {

	protected static final String TAG = ProfileProcessor.class.getSimpleName();

	private TimelineProcessorCallback mCallback;
	private Context mContext;

	
	public TimelineProcessor(Context context) {
		mContext = context;
	}

	
	void getTimeline(TimelineProcessorCallback callback) {

		/*
		Processor is a POJO
			- Processor for each resource type
			- Processor can handle each method on the resource that is supported.
			- Processor needs a callback (which is how the request gets back to the service)
			- Processor uses a RESTMethod - created through a RESTMethodFactory.create(parameterized) or .createGetTimeline()
			
			First iteration had a callback that updated the content provider
			with the resources. But the eventual implementation simply block 
			for the response and do the update.
		 */
		
		// (4) Insert-Update the ContentProvider with a status column and
				// results column
				// Look at ContentProvider example, and build a content provider
				// that tracks the necessary data.

				// (5) Call the REST method
				// Create a RESTMethod class that knows how to assemble the URL,
				// and performs the HTTP operation.

				@SuppressWarnings("unchecked")
				RestMethod<TwitterTimeline> getTimelineMethod = RestMethodFactory.getInstance(mContext).getRestMethod(
						TwitterTimeline.CONTENT_URI, Method.GET, null, null);
				RestMethodResult<TwitterTimeline> result = getTimelineMethod.execute();

				/*
				 * (8) Insert-Update the ContentProvider status, and insert the result
				 * on success Parsing the JSON response (on success) and inserting into
				 * the content provider
				 */

				updateContentProvider(result);

				// (9) Operation complete callback to Service

				callback.send(result.getStatusCode());

			}

			private void updateContentProvider(RestMethodResult<TwitterTimeline> result) {
				
				TwitterTimeline timeline = result.getResource();
				List<Tweet> tweets = timeline.getTweets();
				
				// insert/update row for each Tweet in the TwitterTimline
				for (Tweet tweet : tweets) {
					String message = tweet.getMessage();
					String author = tweet.getAuthorName();
					
					//TODO
				}
				
			}
}