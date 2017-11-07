package com.godmonth.eth.rlp;

import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.testng.annotations.Test;
import org.web3j.protocol.core.methods.request.RawTransaction;

import com.godmonth.eth.rlp.web3j.RawTransactionParser;

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

	@Test
	public void a111() {
		{
			byte[] b = new byte[] { (byte) 0x9f };

			int asIntBigEndian = RlpDecoder.asIntBigEndian(b);
			System.out.println(asIntBigEndian);
		}
	}

	@Test
	public void a3a3() {
		{
			byte[] b = new byte[] { (byte) (0x81) };
			System.out.println(b[0]);
			int asIntBigEndian = RlpDecoder.asIntBigEndian(b);
			System.out.println(asIntBigEndian);
		}
	}

	@Test
	public void testName() throws Exception {
		String s = "f869708504a817c801830f424094ca2b061e4cb4f90f7bf227bd4116692cd0093a198084e8510fc91ba0cb4d04229253707d6c9d521c96d217b26bde199450be225fd26c1f07396cc252a0222dba741c6efc8caabbee65bd4e2301f8e1b2737a3fa96ac67c77aed98c955c";
		RawTransaction parse = RawTransactionParser.parse(s);
		System.out.println(parse.getData());
	}
}
