package sy.rlp;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class RlpDecoder {
	private RlpDecoder() {
	}

	public static RLPElement decode(byte[] input) {
		return decode(ByteBuffer.wrap(input));
	}

	public static RLPElement decode(ByteBuffer byteBuffer) {
		byte b = byteBuffer.get();
		int unsignedInt = Byte.toUnsignedInt(b);
		if (unsignedInt >= 0 && unsignedInt < 128) {
			return new RLPItem(new byte[] { b });
		} else if (unsignedInt >= 128 && unsignedInt < 184) {
			int arrayLength = unsignedInt - 128;
			byte[] subarray = new byte[arrayLength];
			byteBuffer.get(subarray);
			return new RLPItem(subarray);
		} else if (unsignedInt >= 184 && unsignedInt < 192) {
			int arrayLength = getExtendArrayLength(unsignedInt - 184, byteBuffer);
			byte[] subarray = new byte[arrayLength];
			return new RLPItem(subarray);
		} else if (unsignedInt >= 192 && unsignedInt < 247) {
			int listLength = unsignedInt - 192;
			byteBuffer.limit(byteBuffer.position() + listLength);
			List<RLPElement> list = new ArrayList<RLPElement>();

			while (byteBuffer.hasRemaining()) {
				list.add(decode(byteBuffer));
			}
			byteBuffer.limit(byteBuffer.capacity());
			return new RLPList(list);
		} else if (unsignedInt >= 247 && unsignedInt < 256) {
			int listLength = getExtendArrayLength(unsignedInt - 247, byteBuffer);
			byteBuffer.limit(byteBuffer.position() + listLength);

			List<RLPElement> list = new ArrayList<RLPElement>();
			while (byteBuffer.hasRemaining()) {
				list.add(decode(byteBuffer));
			}
			byteBuffer.limit(byteBuffer.capacity());
			return new RLPList(list);
		} else {
			throw new IllegalArgumentException();
		}
	}

	private static int getExtendArrayLength(int arrayLengthLength, ByteBuffer byteBuffer) {
		byte[] arrayLengthArray = new byte[arrayLengthLength];
		byteBuffer.get(arrayLengthArray);
		BigInteger biginteger = new BigInteger(arrayLengthArray);
		return biginteger.intValueExact();
	}

}
