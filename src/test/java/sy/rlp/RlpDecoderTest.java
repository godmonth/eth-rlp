package sy.rlp;

import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.testng.annotations.Test;

public class RlpDecoderTest {

	@Test
	public void decode() {
		RLPElement decode = RlpDecoder
				.decode(new byte[] { (byte) 200, (byte) 131, 97, 98, 99, (byte) 131, 100, 101, 102 });
		System.out.println(decode);
	}

	@Test
	public void d2() {
		String s = "248 88 179 84 104 101 32 108 101 110 103 116 104 32 111 102 32 116 104 105 115 32 115 101 110 116 101 110 99 101 32 105 115 32 109 111 114 101 32 116 104 97 110 32 53 53 32 98 121 116 101 115 44 32 163 73 32 107 110 111 119 32 105 116 32 98 101 99 97 117 115 101 32 73 32 112 114 101 45 100 101 115 105 103 110 101 100 32 105 116";
		String[] split = s.split(" ");
		byte[] bs = new byte[split.length];
		for (int i = 0; i < split.length; i++) {
			int a = Integer.parseInt(split[i]);
			bs[i] = (byte) a;
		}
		System.out.println(ArrayUtils.toString(bs));
		RLPElement rlpElement = RlpDecoder.decode(bs);
		System.out.println(rlpElement);
		RLPList rlpList = (RLPList) rlpElement;
		for (RLPElement element : rlpList.getElements()) {
			RLPItem rlpItem = (RLPItem) element;
			System.out.println(new String(rlpItem.getRLPData()));
		}

	}

	@Test
	public void d3() {
		String s = "248 94 131 97 98 99 248 88 179 84 104 101 32 108 101 110 103 116 104 32 111 102 32 116 104 105 115 32 115 101 110 116 101 110 99 101 32 105 115 32 109 111 114 101 32 116 104 97 110 32 53 53 32 98 121 116 101 115 44 32 163 73 32 107 110 111 119 32 105 116 32 98 101 99 97 117 115 101 32 73 32 112 114 101 45 100 101 115 105 103 110 101 100 32 105 116";
		String[] split = s.split(" ");
		byte[] bs = new byte[split.length];
		for (int i = 0; i < split.length; i++) {
			int a = Integer.parseInt(split[i]);
			bs[i] = (byte) a;
		}
		System.out.println(ArrayUtils.toString(bs));
		RLPElement rlpElement = RlpDecoder.decode(bs);
		System.out.println(rlpElement);
		RLPList rlpList = (RLPList) rlpElement;
		List<RLPElement> elements = rlpList.getElements();
		RLPItem item = (RLPItem) elements.get(0);
		System.out.println(new String(item.getRLPData()));
		RLPList rlpElement2 = (RLPList) elements.get(1);
		for (RLPElement element : rlpElement2.getElements()) {
			RLPItem rlpItem = (RLPItem) element;
			System.out.println(new String(rlpItem.getRLPData()));
		}

	}
}
