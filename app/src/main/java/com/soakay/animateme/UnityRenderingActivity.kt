package com.soakay.animateme

import android.os.Bundle
import android.view.KeyEvent
import com.soakay.animateme.math.Angles
import com.unity3d.player.UnityPlayer
import com.unity3d.player.UnityPlayerActivity

class UnityRenderingActivity : UnityPlayerActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setAngles()
    }

    private fun setAngles() {
        UnityPlayer.UnitySendMessage(
                "JointManipulator",
                "ApplyAngleToJoint",
                "rightShoulder:" + intent.getFloatExtra("rightShoulder", 0.0f)
        )
        UnityPlayer.UnitySendMessage(
                "JointManipulator",
                "ApplyAngleToJoint",
                "leftShoulder:" + intent.getFloatExtra("leftShoulder", 0.0f)
        )
        UnityPlayer.UnitySendMessage(
                "JointManipulator",
                "ApplyAngleToJoint",
                "leftElbow:" + intent.getFloatExtra("leftElbow", 0.0f)
        )
        UnityPlayer.UnitySendMessage(
                "JointManipulator",
                "ApplyAngleToJoint",
                "rightElbow:" + intent.getFloatExtra("rightElbow", 0.0f)
        )

        UnityPlayer.UnitySendMessage(
            "JointManipulator",
            "ApplyAngleToJoint",
            "leftLeg:" + intent.getFloatExtra("leftLeg", 0.0f)
        )
        UnityPlayer.UnitySendMessage(
            "JointManipulator",
            "ApplyAngleToJoint",
            "rightLeg:" + intent.getFloatExtra("rightLeg", 0.0f)
        )
        UnityPlayer.UnitySendMessage(
            "JointManipulator",
            "ApplyAngleToJoint",
            "rightKneecap:" + intent.getFloatExtra("leftKneecap", 0.0f)
        )
        UnityPlayer.UnitySendMessage(
            "JointManipulator",
            "ApplyAngleToJoint",
            "leftKneecap:" + intent.getFloatExtra("rightKneecap", 0.0f)
        )
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        UnityPlayer.currentActivity.finish()
        return super.onKeyDown(keyCode, event)
    }
}