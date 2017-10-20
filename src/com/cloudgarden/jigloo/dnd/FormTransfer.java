package com.cloudgarden.jigloo.dnd;

import org.eclipse.swt.dnd.ByteArrayTransfer;
import org.eclipse.swt.dnd.TransferData;

import com.cloudgarden.jigloo.outline.TreeParent;
/**
 * Class for serializing gadgets to/from a byte array
 */
public class FormTransfer extends ByteArrayTransfer {
	private static FormTransfer instance = new FormTransfer();
	private static final String TYPE_NAME = "form-component-transfer-format";
	private static final int TYPEID = registerType(TYPE_NAME);

	/**
	 * Returns the singleton gadget transfer instance.
	 */
	public static FormTransfer getInstance() {
		return instance;
	}
	/**
	 * Avoid explicit instantiation
	 */
	private FormTransfer() {}

	protected TreeParent[] fromByteArray(byte[] bytes) {
//		if (true)
			return null;
//		DataInputStream in =
//			new DataInputStream(new ByteArrayInputStream(bytes));
//
//		try {
//			/* read number of gadgets */
//			int n = in.readInt();
//			/* read gadgets */
//			TreeParent[] gadgets = new TreeParent[n];
//			for (int i = 0; i < n; i++) {
//				TreeParent gadget = readGadget(null, in);
//				if (gadget == null) {
//					return null;
//				}
//				gadgets[i] = gadget;
//			}
//			return gadgets;
//		} catch (IOException e) {
//			return null;
//		}
	}
	/*
	 * Method declared on Transfer.
	 */
	protected int[] getTypeIds() {
		return new int[] { TYPEID };
	}
	/*
	 * Method declared on Transfer.
	 */
	protected String[] getTypeNames() {
		return new String[] { TYPE_NAME };
	}
	
	private Object data;
	
	/*
	 * Method declared on Transfer.
	 */
	protected void javaToNative(Object object, TransferData transferData) {
		data = transferData;
//		if (true)
			return;
//		byte[] bytes = toByteArray((TreeParent[]) object);
//		if (bytes != null)
//			super.javaToNative(bytes, transferData);
	}
	/*
	 * Method declared on Transfer.
	 */
	protected Object nativeToJava(TransferData transferData) {
		//System.out.println("N2J "+transferData);
//		if (true)
			return data;
			
//		byte[] bytes = (byte[]) super.nativeToJava(transferData);
//		return fromByteArray(bytes);
	}
	/**
	 * Reads and returns a single gadget from the given stream.
	 */
//	private TreeParent readGadget(TreeParent parent, DataInputStream dataIn)
//		throws IOException {
//		//if (true)
//			//return null;
//		/**
//		 * Gadget serialization format is as follows:
//		 * (String) name of gadget
//		 * (int) number of child gadgets
//		 * (Gadget) child 1
//		 * ... repeat for each child
//		 */
//		String name = dataIn.readUTF();
//		int n = dataIn.readInt();
//		TreeParent newParent = new TreeParent(name);
//		newParent.setParent((TreeParent) parent);
//		for (int i = 0; i < n; i++) {
//			readGadget(newParent, dataIn);
//		}
//		return newParent;
//	}
//	protected byte[] toByteArray(TreeParent[] gadgets) {
//		//if (true)
//			//return null;
//		/**
//		 * Transfer data is an array of gadgets.  Serialized version is:
//		 * (int) number of gadgets
//		 * (Gadget) gadget 1
//		 * (Gadget) gadget 2
//		 * ... repeat for each subsequent gadget
//		 * see writeGadget for the (Gadget) format.
//		 */
//		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
//		DataOutputStream out = new DataOutputStream(byteOut);
//
//		byte[] bytes = null;
//
//		try {
//			/* write number of markers */
//			out.writeInt(gadgets.length);
//
//			/* write markers */
//			for (int i = 0; i < gadgets.length; i++) {
//				writeGadget((TreeObject) gadgets[i], out);
//			}
//			out.close();
//			bytes = byteOut.toByteArray();
//		} catch (IOException e) {
//			//when in doubt send nothing
//		}
//		return bytes;
//	}
//	/**
//	 * Writes the given gadget to the stream.
//	 */
//	private void writeGadget(TreeObject gadget, DataOutputStream dataOut)
//		throws IOException {
//		//if (true)
//			//return;
//		/**
//		 * Gadget serialization format is as follows:
//		 * (String) name of gadget
//		 * (int) number of child gadgets
//		 * (Gadget) child 1
//		 * ... repeat for each child
//		 */
//		dataOut.writeUTF(gadget.getName());
//		TreeObject[] children =
//			(TreeObject[]) ((TreeParent) gadget).getChildren();
//		dataOut.writeInt(children.length);
//		for (int i = 0; i < children.length; i++) {
//			writeGadget(children[i], dataOut);
//		}
//	}
}