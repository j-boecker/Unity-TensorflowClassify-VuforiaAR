using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using System.Linq;
using System;
using Vuforia;
using System.IO;

public class CatPic : MonoBehaviour {

    public RawImage background;

    private int imageHeight;
    private int imageWidth;
    private double timeCounter;
    private int imageNameCounter;
    private string fileName = "Edge";

    Texture2D camTextureSaved;
    Texture2D camTexture;
    Texture2D destTex;
    Texture2D saveToFileTex;

    void ProcessImage()
    {
        camTexture = (Texture2D)background.texture;
        imageHeight = Convert.ToInt32(camTexture.height);
        imageWidth = Convert.ToInt32(camTexture.width);
        //getting right pxiel ratios

        //convert to two D pixel array
        Color32[,] twoDImageData = GetTwoDColor(camTexture, imageWidth, imageHeight);

        // apply edge filter
        Color32[,] edgeImageData2 = EdgeFilter(twoDImageData, imageWidth, imageHeight);


        //convert back to one D pixel array
        Color32[] oneDImageData = GetOneDColor(edgeImageData2, imageWidth, imageHeight);

        destTex = new Texture2D(imageWidth, imageHeight);
        destTex.SetPixels32(oneDImageData);
        destTex.Apply();

        // Set the current object's texture to show the
        // rotated image.
        //background.texture = (Texture)destTex;

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


        for (int h = 1; h < imageHeight - 1; h++)
        {
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
                    imageData[h, w].r = Convert.ToByte(Math.Min(255, Math.Abs(4 * intensity[h, w]
                            - (intensity[h + 1, w] + intensity[h - 1, w] + intensity[h, w + 1] + intensity[h, w - 1]))));

                    imageData[h, w].g = 0;

                    imageData[h, w].b = 0;

                    // make black bckground transparant
                    if (imageData[h, w].r < 20)
                    {
                        imageData[h, w].a = 0;
                    }
                    else if (imageData[h, w].r < 50)
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

    Color32[,] GetTwoDColor(Texture2D sourceTex, int imageWidth, int imageHeight)
    {
        var pix = sourceTex.GetPixels32();
        Color32[,] imageData = new Color32[imageHeight, imageWidth];
        int height = imageHeight - 1;
        int width = 0;
        for (int i = 0; i <= imageHeight * imageWidth; i++)
        {
            if (height >= 0 && width < imageWidth)
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
        for (int h = 0; h <= imageHeight; h++)
        {
            for (int w = 0; w <= imageWidth; w++)
            {
                if (counter < imageHeight * imageWidth && h < imageHeight && w < imageWidth)
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
        Color32[] imageData = new Color32[350 * 350];

        int counter = 0;
        for (int h = 0; h <= 350; h++)
        {
            for (int w = 0; w <= 350; w++)
            {
                if (counter < 350 * 350 && h < 350 && w < 350)
                {
                    imageData[counter] = twoDimageArray[h, w];
                    counter++;
                }

            }
        }
        return imageData;
    }

 

    void SaveToPng()
    {
        camTextureSaved = (Texture2D)background.texture;
        int imageHeightSaved = Convert.ToInt32(camTexture.height);
        int imageWidthSaved = Convert.ToInt32(camTexture.width);
        //getting right pxiel ratios
        double ratio = imageWidthSaved / imageHeightSaved;

        imageHeightSaved = 350;
        imageWidthSaved = Convert.ToInt32(350 * ratio);

        camTexture = TextureTools.CropTexture(camTexture);
        camTextureSaved = TextureTools.scaled(camTextureSaved, imageWidthSaved, imageHeightSaved, FilterMode.Trilinear);
        Debug.Log(imageHeightSaved + "   " + imageWidthSaved);

        //convert to two D pixel array
        Color32[,] twoDImageData = GetTwoDColor(camTextureSaved, imageWidthSaved, imageHeightSaved);

        // apply edge filter
        Color32[,] edgeImageData = EdgeFilter(twoDImageData, imageWidthSaved, imageHeightSaved);
  

        //convert back to one D pixel array
        Color32[] oneDSaveToFileImageData = GetOneDColor224(edgeImageData, imageWidthSaved, imageHeightSaved);

        saveToFileTex = new Texture2D(350, 350);
        saveToFileTex.SetPixels32(oneDSaveToFileImageData);
        saveToFileTex.Apply();

        fileName = "catpic";
        byte[] pngBytes = saveToFileTex.EncodeToPNG();
        File.WriteAllBytes(Application.persistentDataPath + "/" + fileName + imageNameCounter + ".png", pngBytes);

        Debug.Log("Saved to " + Application.persistentDataPath + "/" + "R_T_" + fileName + imageNameCounter + ".png");
    
        imageNameCounter++;
    }

    // Use this for initialization
    void Start () {
        ProcessImage();
        SaveToPng();
	}
	
	// Update is called once per frame
	void Update () {
		
	}
}
