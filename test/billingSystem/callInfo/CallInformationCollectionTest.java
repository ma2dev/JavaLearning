package billingSystem.callInfo;

import static org.junit.Assert.*;

import java.text.ParseException;

import org.junit.Test;

public class CallInformationCollectionTest {

	@Test
	public final void testデータセット() {
		String srcSubscriber = new String("09076228838");
		String dstSubscriber = new String("09012345678");
		String startTime = new String("20130421210000");
		String endTime = new String("20130421210100");
		String reason = new String("0");

		try {
			CallInformation callInformation = new CallInformation(srcSubscriber, dstSubscriber, startTime, endTime, reason);
			CallInformationCollection collection = new CallInformationCollection(callInformation);

			dstSubscriber = new String("07012345678");
			callInformation = new CallInformation(srcSubscriber, dstSubscriber, startTime, endTime, reason);
			boolean result = collection.add(callInformation);
			assertEquals(true, result);

			srcSubscriber = new String("09176228838");
			callInformation = new CallInformation(srcSubscriber, dstSubscriber, startTime, endTime, reason);
			result = collection.add(callInformation);
			assertEquals(false, result);

		} catch (ParseException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

}
