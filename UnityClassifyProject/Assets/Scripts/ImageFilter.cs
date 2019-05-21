using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using System.Linq;
using System;
using Vuforia;
using System.IO;

public class ImageFilter : MonoBehaviour {

    Texture2D camTextureSaved;
    Texture2D camTexture;
    Texture2D destTex;
    Texture2D saveToFileTex;

    public CameraFeedBehavior camFeed;
    public RawImage background;
    public AspectRatioFitter fit;
    public InputField fileNameField;
    public MessageBehavior messageBehavior;

    private int imageHeight;
    private int imageWidth;
    private double timeCounter;
    private int imageNameCounter;
    private string fileName = "Edge";


    // Use this for initialization
    void Start () {
    
        timeCounter = 0;
        imageNameCounter = 0;
    }
	
    void ProcessImage()
    {
        camTexture = camFeed.GetImageTexture();
        imageHeight = Convert.ToInt32(camTexture.height);
        imageWidth = Convert.ToInt32(camTexture.width);
        //getting right pxiel ratios

        //convert to two D pixel array
        Color32[,] twoDImageData = GetTwoDColor(camTexture, imageWidth,imageHeight);

        // apply edge filter
        Color32[,] edgeImageData2 = EdgeFilter(twoDImageData, imageWidth, imageHeight);


        //convert back to one D pixel array
        Color32[] oneDImageData = GetOneDColor(edgeImageData2, imageWidth, imageHeight);

        destTex = new Texture2D(imageWidth, imageHeight);
        destTex.SetPixels32(oneDImageData);
        destTex.Apply();

      

        // Set the current object's texture to show the
        // rotated image.
        background.texture = (Texture)destTex;
    }

    Color32[,] EdgeFilter(Color32[,] twoDImagearray, int imageWidth, int imageHeight)
    {

       Double[,] intensity = new Double[imageHeight, imageWidth];
       Color32[,] imageData = new Color32[imageHeight, imageWidth];

      

        //making the image to a gryscale image
        for (int h = 0; h < imageHeight; h++)
            for (int w = 0; w < imageWidth; w++)
            {
                intensity[h, w] = 0;
                intensity[h, w] += twoDImagearray[h, w].r * 0.2126;
                intensity[h, w] += twoDImagearray[h, w].g * 0.7152;
                intensity[h, w] += twoDImagearray[h, w].b * 0.0722;

            }

        //blurring
       // intensity = SimpleBlurGreyScale(intensity, 1);


        for (int h = 1; h < imageHeight - 1; h++) { 
                for (int w = 1; w < imageWidth - 1; w++)
                {
                // Substracts the intensity values from 4 pixels around the
                // pixel from the the current pixel times 4.
                // If they differ a lot from the current pixel there is and edge
                // and the current pixel is set to light gray.
                // The result has to be an absolute value. For example, if the
                // current pixel is lighter,
                // than the average of the surrounding pixels the result will be
                // a negative value, which can't be an RGB value.
                    try
                    {
                        imageData[h, w].r = Convert.ToByte(Math.Min(255,Math.Abs(4 * intensity[h, w]
                                - (intensity[h + 1, w] + intensity[h - 1, w] + intensity[h, w + 1] + intensity[h, w - 1]))));

                        imageData[h, w].g = 0;

                        imageData[h, w].b = 0;
                        
                    // make black bckground transparant
                        if(imageData[h,w].r < 20)
                        {
                            imageData[h, w].a = 0;
                        }
                        else if(imageData[h, w].r < 50)
                        {
                            imageData[h, w].a = 200;
                        }
                        else 
                        {
                            imageData[h, w].a = 255;
                        } 
                        
                    }
                     catch (Exception)
                    {
                    Debug.Log(Math.Abs(4 * intensity[h, w]
                                - (intensity[h + 1, w] + intensity[h - 1, w] + intensity[h, w + 1] + intensity[h, w - 1])));
    }
                }

            }

        return imageData;
    }

    Double[,] SimpleBlurGreyScale(Double[,] intensity, int nTimes)
    {
        Double[,] imageData = new Double[imageHeight, imageWidth];

        for (int n = 0; n <= nTimes;n++)
        {
            for (int h = 1; h < imageHeight - 1; h++)
            {
                for (int w = 1; w < imageWidth - 1; w++)
                {
                    imageData[h, w] = (intensity[h - 1, w] + intensity[h + 1, w] + intensity[h, w - 1]
                                 + intensity[h, w + 1] + intensity[h, w]) / 5;

                }
            }
        }
        return imageData;
    }

    Color32[,] BlurFilter(Color32[,] twoDImagearray)
    {

        Color32[,] imageData = new Color32[imageHeight, imageWidth];

        for (int counter = 0; counter < 2; counter++) // executes filter
                                                      // "counter" (n) times
        {
            for (int h = 1; h < imageHeight - 1; h++) // goes through pixels
                                                    // vertically
            {

                for (int w = 1; w < imageWidth - 1; w++) // goes through pixels
                                                       // horizontally

                {
                    imageData[h,w].r = Convert.ToByte((twoDImagearray[h-1,w].r + twoDImagearray[h + 1, w].r + twoDImagearray[h, w-1].r
                            + twoDImagearray[h, w-1].r + twoDImagearray[h, w].r) / 5);

                    imageData[h, w].g = Convert.ToByte((twoDImagearray[h - 1, w].g + twoDImagearray[h + 1, w].g + twoDImagearray[h, w - 1].g
                             + twoDImagearray[h, w - 1].g + twoDImagearray[h, w].g) / 5);

                    imageData[h, w].b = Convert.ToByte((twoDImagearray[h - 1, w].b + twoDImagearray[h + 1, w].b + twoDImagearray[h, w - 1].b
                            + twoDImagearray[h, w - 1].b + twoDImagearray[h, w].b) / 5);

                    imageData[h, w].a = 255;

                    // original selected pixel rgb value gets overriden with the
                    // average rgb value of the original selected pixel rgb
                    // value added to
                    // neighbouring pixels
                }

            }
        }

        return imageData;
    }



    Color32[,] CutOutFilter(Color32[,] twoDImagearray, int imageWidth, int imageHeight)
    {

        Color32[,] imageData = new Color32[224, 224];

        for (int h = 0; h < 224; h++) 
            {
     
                for (int w = 0; w < 224; w++) 
                {
                     imageData[h, w] = twoDImagearray[h+10, imageWidth/2 - 112];
                }
            }

        return imageData;
    }


    Color32[,] GetTwoDColor(Texture2D sourceTex, int imageWidth, int imageHeight)
    {
        var pix = sourceTex.GetPixels32();
        Color32[,] imageData = new Color32[imageHeight,imageWidth];
        int height = imageHeight-1;
        int width = 0;
        for (int i = 0; i <= imageHeight*imageWidth; i++)
        {
            if (height >= 0  && width < imageWidth)
            {

                imageData[height, width] = pix[i];
                width++;
                if (i % imageWidth - 1 == 0)
                {
                    height--;
                    width = 0;
                }
            }
           
        }

        return imageData;
    }

    Color32[] GetOneDColor(Color32[,] twoDimageArray, int imageWidth, int imageHeight)
    {
        Color32[] imageData = new Color32[imageWidth * imageHeight];

        int counter = 0;
        for(int h = 0; h <= imageHeight; h++)
        {
            for(int w = 0; w <= imageWidth; w++)
            {
                if (counter < imageHeight * imageWidth && h< imageHeight && w < imageWidth)
                {
                    imageData[counter] = twoDimageArray[h, w];
                    counter++;
                }
               
            }
        }
        return imageData;
    }

    Color32[] GetOneDColor224(Color32[,] twoDimageArray, int imageWidth, int imageHeight)
    {
        Color32[] imageData = new Color32[224 * 224];

        int counter = 0;
        for (int h = 0; h <= 224; h++)
        {
            for (int w = 0; w <= 224; w++)
            {
                if (counter < 224 * 224 && h < 224 && w < 224)
                {
                    imageData[counter] = twoDimageArray[h, w];
                    counter++;
                }

            }
        }
        return imageData;
    }

    Color32[] GetBWjpg(Color32[] oneDImageArray)
    {
        Color32[] imageData = new Color32[oneDImageArray.Length];

        for(int i = 0; i<oneDImageArray.Length; i++)
        {
            if(oneDImageArray[i].r < 10)
            {
                imageData[i].r = 255;
                imageData[i].g = 255;
                imageData[i].b = 255;
            }
            else if(oneDImageArray[i].r < 30)
            {
                Byte blackGrade = Math.Min(Convert.ToByte(Math.Abs(255 - oneDImageArray[i].r * 5)), (byte)255);
                imageData[i].r = blackGrade;
                imageData[i].g = blackGrade;
                imageData[i].b = blackGrade;
            }
            else
            {
                imageData[i].r = 0;
                imageData[i].g = 0;
                imageData[i].b = 0;
            }
            imageData[i].a = 255;
        }

        return imageData;
    }

    void SaveToPng()
    {

        camTextureSaved = camFeed.GetImageTexture();

        int imageHeightSaved = Convert.ToInt32(camTexture.height);
        int imageWidthSaved = Convert.ToInt32(camTexture.width);
        //getting right pxiel ratios
        double ratio = imageWidthSaved / imageHeightSaved;

        imageHeightSaved = 244;
        imageWidthSaved = Convert.ToInt32(244 * ratio);

        camTexture = TextureTools.CropTexture(camTexture);
        camTextureSaved = TextureTools.scaled(camTextureSaved, imageWidthSaved, imageHeightSaved, FilterMode.Trilinear);
        Debug.Log(imageHeightSaved + "   " + imageWidthSaved);

        //convert to two D pixel array
        Color32[,] twoDImageData = GetTwoDColor(camTextureSaved, imageWidthSaved, imageHeightSaved);

        // apply edge filter
        Color32[,] edgeImageData = EdgeFilter(twoDImageData, imageWidthSaved, imageHeightSaved);

        //cutout for saving to file
        Color32[,] cutOutImageData = CutOutFilter(edgeImageData, imageWidthSaved, imageHeightSaved);

        //convert back to one D pixel array
        Color32[] oneDSaveToFileImageData = GetOneDColor224(edgeImageData, imageWidthSaved, imageHeightSaved);

        saveToFileTex = new Texture2D(224, 224);
        saveToFileTex.SetPixels32(oneDSaveToFileImageData);
        saveToFileTex.Apply();

        fileName = fileNameField.text;
        byte[] pngBytes = saveToFileTex.EncodeToPNG();
        File.WriteAllBytes(Application.persistentDataPath + "/" + fileName + imageNameCounter + ".png", pngBytes);


        Color32[] BWjpgImageData = GetBWjpg(oneDSaveToFileImageData);
        saveToFileTex.SetPixels32(BWjpgImageData);
        saveToFileTex.Apply();

        byte[] jpgBytes = saveToFileTex.EncodeToJPG(100);
        File.WriteAllBytes(Application.persistentDataPath + "/" + "B_W_" + fileName + imageNameCounter + ".jpg", jpgBytes);

        Debug.Log("Saved to " + Application.persistentDataPath + "/"+ "R_T_" + fileName + imageNameCounter + ".png");
        
        imageNameCounter++;
    }
    // Update is called once per frame
    void Update () {


        if (Input.GetMouseButtonDown(0))
        {
            ProcessImage();
        }
        if (camTexture)
        {
            float ratio = (float)camTexture.width / (float)camTexture.height;
            fit.aspectRatio = ratio;
        }
      
    }
}
