using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using System.Linq;
using System;

public class ImageFilter : MonoBehaviour {

    Texture2D camTexture;

    public CameraFeedBehavior camFeed;
    public RawImage background;

    private int imageHeight;
    private int imageWidth;

  

	// Use this for initialization
	void Start () {
   
       
    }
	
    void ProcessImage()
    {
        camTexture = camFeed.GetImageTexture();

        imageHeight = Convert.ToInt32(camTexture.height*0.5);
        imageWidth = Convert.ToInt32(camTexture.width*0.5);

       // camTexture = TextureTools.CropTexture(camTexture);
        camTexture = TextureTools.scaled(camTexture, imageWidth, imageHeight, FilterMode.Trilinear);

        Color32[,] twoDImageData = GetTwoDColor(camTexture);

        Color32[,] edgeImageData = EdgeFilter(twoDImageData);

        Color32[] oneDImageData = GetOneDColor(edgeImageData);
        // Copy the reversed image data to a new texture.
        var destTex = new Texture2D(imageWidth, imageHeight);
        destTex.SetPixels32(oneDImageData);
        destTex.Apply();

        // Set the current object's texture to show the
        // rotated image.
        background.texture = (Texture)destTex;

    }

    Color32[,] EdgeFilter(Color32[,] twoDImagearray)
    {

       Double[,] intensity = new Double[imageHeight, imageWidth];
       Color32[,] imageData = new Color32[imageHeight, imageWidth];
        for (int h = 0; h < imageHeight; h++)
            for (int w = 0; w < imageWidth; w++)
            {
                intensity[h, w] = 0;
                intensity[h, w] += twoDImagearray[h, w].r * 0.2126;
                intensity[h, w] += twoDImagearray[h, w].g * 0.7152;
                intensity[h, w] += twoDImagearray[h, w].b * 0.0722;
                if (h< 10 && intensity[h, w] > 0)
                {
                 //   Debug.Log(intensity[h, w]);
                }

            }

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

                        imageData[h, w].a = 255;
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


    Color32[,] GetTwoDColor(Texture2D sourceTex)
    {
        var pix = sourceTex.GetPixels32();
        Color32[,] imageData = new Color32[imageHeight,imageWidth];
        int height = 0;
        int width = 0;
        for (int i = 0; i <= imageHeight*imageWidth; i++)
        {
            if (height < imageHeight && width < imageWidth)
            {

                imageData[height, width] = pix[i];
                width++;
                if (i % imageWidth - 1 == 0)
                {
                    height++;
                    width = 0;
                }
            }
           
        }

        return imageData;
    }

    Color32[] GetOneDColor(Color32[,] twoDimageArray)
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

    // Update is called once per frame
    void Update () {
        if (Input.GetMouseButtonDown(0))
        {
            ProcessImage();
        }
    }
}
