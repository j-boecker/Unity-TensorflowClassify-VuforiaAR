using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class MessageBehavior : MonoBehaviour {

	public Text uiText;
    public TextMesh textM1;
    public TextMesh textM2;
    public TextMesh textM3;
    public Material mat1;
    public Texture tex1;
    public Texture tex2;

	private Vector3 showPosition = new Vector3 (0, 240f, 0);
	private Vector3 hidePosition = new Vector3 (0, 330f, 0);
	private Vector3 desiredPosition;

	private void Awake () {
		desiredPosition = hidePosition;
	}

	private void Update () {
		transform.localPosition = Vector3.Lerp (transform.localPosition, desiredPosition, 6F * Time.deltaTime);
	}

	public void ShowMessage (string message, string message2, string message3) {

        if (message.Contains("a"))
        {
            mat1.SetTexture("_MainTex",tex1);
        }
        else
        {
            mat1.SetTexture("_MainTex", tex2);
        }
        textM1.text = message;
        textM2.text = message2;
        textM3.text = message3;
		HideMessage ();
		desiredPosition = showPosition;
		uiText.text = message;
		if (DelayCoroutine != null) {
			StopCoroutine (DelayCoroutine);
		}
		DelayCoroutine = StartCoroutine (DelayHideMessage ());
	}

	Coroutine DelayCoroutine;
	IEnumerator DelayHideMessage () {
		yield return new WaitForSeconds (2f);
		HideMessage ();
		DelayCoroutine = null;
	}

	void HideMessage () {
		desiredPosition = hidePosition;
		uiText.text = "";
	}
}
