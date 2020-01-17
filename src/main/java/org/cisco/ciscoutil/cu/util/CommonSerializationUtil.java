package org.cisco.ciscoutil.cu.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.Base64;

import javax.imageio.ImageIO;

public class CommonSerializationUtil<T> {
	
	public byte[] serialize(T obj) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutput out = null;
		out = new ObjectOutputStream(bos);   
		out.writeObject(obj);
		out.flush();
		return bos.toByteArray();
	}
	
	@SuppressWarnings("unchecked")
	public T deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		ObjectInput in = null;
		in = new ObjectInputStream(bis);
		return (T) in.readObject(); 
	}
	
	public byte[] serializeImage(String imagePath) throws IOException {
		return serializeImage(new File(imagePath), imagePath.substring(imagePath.lastIndexOf(".") + 1));
	}
	
	public byte[] serializeImage(File imageFile, String imageExtension) throws IOException {
		BufferedImage image = ImageIO.read(imageFile); 
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    ImageIO.write(image, imageExtension, baos);
	    return baos.toByteArray(); 
	}
	
	public String serializeImageToBase64String(String imagePath) throws IOException {
		byte[] bytes = serializeImage(imagePath);
		return toBase64(bytes);
	}

	public String toBase64(byte[] bytes) {
		return Base64.getEncoder().encodeToString(bytes);
	}
}
