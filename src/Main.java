import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    public static void main(String[] args) {

        //Reading Image and Storing Colors
        try {
            File inputImage = new File(
                    "C:\\Users\\Hp\\Desktop\\Programming\\Java\\Data Mining\\K Means (Image Pixel Clustering)\\Input.jpg");
            BufferedImage image = ImageIO.read(inputImage);
            int width = image.getWidth();
            int height = image.getHeight();

            //keeping value of all colors
            ArrayList <Color> colorArray = new ArrayList<>();

            for(int i=0; i<height; i++) {

                for(int j=0; j<width; j++) {

                    Color c = new Color(image.getRGB(j, i));
                    colorArray.add(c);

                }
            }

            //taking value of K
            System.out.print("Enter value of K: ");
            Scanner input = new Scanner(System.in);
            int K = input.nextInt();

            //taking random values for every K
            Color[] centroidArray = new Color[K];
            Color[] newCentroidArray = new Color[K];

            for(int i=0; i<K; i++)
            {
                int randomNumber = ThreadLocalRandom.current().nextInt(0, colorArray.size()-1);
                centroidArray[i] = colorArray.get(randomNumber);

                newCentroidArray[i] = null;
            }

            ArrayList<Color>[] clusteredArray = new ArrayList[K];

            for(int z=0; z<K; z++)
            {
                clusteredArray[z] = new ArrayList<Color>();
            }

            while(true)
            {
                //Measure distance between every point and initial k values and clustering
                for(int i3=0; i3<colorArray.size(); i3++)
                {
                    int distance = 999999;
                    int tempDistance;
                    int clusteringIndex = -1;

                    for(int i4=0; i4<K; i4++)
                    {
                        tempDistance = (int) Math.sqrt(Math.pow(centroidArray[i4].getRed() - colorArray.get(i3).getRed(), 2)
                                + Math.pow(centroidArray[i4].getRed() - colorArray.get(i3).getRed(), 2)
                                + Math.pow(centroidArray[i4].getRed() - colorArray.get(i3).getRed(), 2));

                        if (tempDistance < distance)
                        {
                            distance = tempDistance;
                            clusteringIndex = i4;
                        }

                        if (i4 == K-1)
                        {
                            clusteredArray[clusteringIndex].add(colorArray.get(i3));
                        }
                    }
                }

                //for(int q=0; q<K; q++) System.out.println("Cluster-" + (q+1) +"size: " + clusteredArray[q].size());

                //Finding Mean
                for(int i5=0; i5<K; i5++)
                {
                    int red = 0;
                    int green = 0;
                    int blue = 0;

                    for(int i6=0; i6<clusteredArray[i5].size(); i6++)
                    {
                        //System.out.println("red: " + clusteredArray[i5].get(i6).getRed());

                        red = red + clusteredArray[i5].get(i6).getRed();
                        blue = blue + clusteredArray[i5].get(i6).getBlue();
                        green = green + clusteredArray[i5].get(i6).getGreen();
                    }

                    red=red/(clusteredArray[i5].size()+1);
                    blue=blue/(clusteredArray[i5].size()+1);
                    green=green/(clusteredArray[i5].size()+1);

                    Color newCentroid = new Color(red,green,blue);

                    //System.out.println("Old Centroid: " + centroidArray[i5]);
                    newCentroidArray[i5] = newCentroid;
                    //System.out.println("New Centroid: " + newCentroidArray[i5]);
                }

                int check=0;
                //Checking difference of Old and New Centroids
                for(int i7=0; i7<K; i7++)
                {
                    if(Math.abs(centroidArray[i7].getGreen()-newCentroidArray[i7].getGreen())<1)
                    {
                        if(Math.abs(centroidArray[i7].getRed()-newCentroidArray[i7].getRed())<1)
                        {
                            if(Math.abs(centroidArray[i7].getBlue()-newCentroidArray[i7].getBlue())<1)
                            {
                                check++;
                            }
                        }
                    }
                }

                if(check==K-1)
                {
                    //System.out.println("Break paisi");
                    break;
                }
                else centroidArray = newCentroidArray.clone();
            }

            //System.out.println("While shesh");

            //Make new image
            //File inputImage2 = new File("C:\\Users\\iit\\Desktop\\ibtd\\0151.jpg");
            BufferedImage image2 = ImageIO.read(inputImage);
            BufferedImage modifiedImage = ImageIO.read(inputImage);
            int width2 = image2.getWidth();
            int height2 = image2.getHeight();

            //System.out.println(width2);
            //System.out.println(height2);

            for(int ii=0; ii<height2; ii++)
            {
                for(int jj=0; jj<width2; jj++)
                {
                    Color c = new Color(image.getRGB(jj, ii));

                    for(int kk=0; kk<K; kk++)
                    {
                        if(clusteredArray[kk].contains(c))
                        {
                            modifiedImage.setRGB(jj, ii, centroidArray[kk].getRGB());
                            break;
                        }
                    }
                    //System.out.println("vitor-1");
                }
                //System.out.println("vitor-2");
            }
            //System.out.println("loops shesh");

            ImageIO.write(modifiedImage, "jpg", new File(
                    "C:\\Users\\Hp\\Desktop\\Programming\\Java\\Data Mining\\K Means (Image Pixel Clustering)\\Output.jpg"));

        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}