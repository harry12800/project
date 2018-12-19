package cn.harry12800.vchat.client;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;

/**  
 * 使图片水平翻转、垂直翻转、旋转180度  
 * @author Administrator  
 */
public class TestPicture {

	public static void main(String[] args) throws IOException {
		BufferedImage sourceImage = ImageIO.read(new FileInputStream("Z:/Workspaces/harry12800.LnkTools/src/image/IOS.png"));
		BufferedImage dstImage = null;
		AffineTransform transform = new AffineTransform(-1, 0, 0, 1, sourceImage.getWidth() - 1, 0);//水平翻转   
		//        AffineTransform transform = new   AffineTransform(1,0,0,-1,0,sourceImage.getHeight()-1);//垂直翻转   
		//         AffineTransform transform = new   AffineTransform(-1,0,0,-1,sourceImage.getWidth()-1,sourceImage.getHeight()-1);//旋转180度   
		AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
		dstImage = op.filter(sourceImage, null);

		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.add("Source Transform", new JLabel(new ImageIcon(sourceImage)));
		tabbedPane.add("Affine Transform", new JLabel(new ImageIcon(dstImage)));

		JFrame jframe = new JFrame();
		jframe.setSize(800, 600);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.getContentPane().add(tabbedPane);
		jframe.setVisible(true);
	}
}
