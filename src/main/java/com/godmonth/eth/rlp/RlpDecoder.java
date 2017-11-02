package com.godmonth.eth.rlp;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

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
			if (arrayLength > byteBuffer.remaining()) {
				throw new IllegalArgumentException("arrayLength:" + arrayLength + "," + byteBuffer.toString());
			}
			byte[] subarray = new byte[arrayLength];
			byteBuffer.get(subarray);
			return new RLPItem(subarray);
		} else if (unsignedInt >= 184 && unsignedInt < 192) {
			int arrayLength = getExtendArrayLength(unsignedInt - 183, byteBuffer);
			byte[] subarray = new byte[arrayLength];
			byteBuffer.get(subarray);
			return new RLPItem(subarray);
		} else if (unsignedInt >= 192 && unsignedInt < 247) {
			int listLength = unsignedInt - 192;
			int innerLimit = byteBuffer.position() + listLength;
			List<RLPElement> list = new ArrayList<RLPElement>();
			while (byteBuffer.position() < innerLimit) {
				list.add(decode(byteBuffer));
			}
			return new RLPList(list);
		} else if (unsignedInt >= 247 && unsignedInt < 256) {
			int listLength = getExtendArrayLength(unsignedInt - 247, byteBuffer);
			int innerLimit = byteBuffer.position() + listLength;

			List<RLPElement> list = new ArrayList<RLPElement>();
			while (byteBuffer.position() < innerLimit) {
				list.add(decode(byteBuffer));
			}
			return new RLPList(list);
		} else {
			throw new IllegalArgumentException();
		}
	}

	private static int getExtendArrayLength(int arrayLengthLength, ByteBuffer byteBuffer) {
		byte[] arrayLengthArray = new byte[arrayLengthLength];
		byteBuffer.get(arrayLengthArray);
		return asIntBigEndian(arrayLengthArray);
	}

	public static int asIntBigEndian(byte[] raw) {
		if (raw.length > 4) {
			throw new IllegalArgumentException("int only");
		}
		byte[] i = raw;
		if (raw.length < 4) {
			i = ArrayUtils.addAll(new byte[4 - raw.length], raw);
		}
		ByteBuffer buffer = ByteBuffer.wrap(i, 0, 4);
		buffer.order(ByteOrder.BIG_ENDIAN);
		return buffer.getInt();
	}
}
