package service.AAADEVCloudServices.Google.TTS.MediaListener;

import java.util.UUID;

import com.avaya.collaboration.call.Call;
import com.avaya.collaboration.call.media.MediaListenerAbstract;
import com.avaya.collaboration.call.media.PlayOperationCause;

public class MediaListenerAudio extends MediaListenerAbstract{
	private final Call call;
	
	public MediaListenerAudio(final Call call) {
		super();
		this.call = call;
	}

	@Override
	public void playCompleted(UUID requestId, PlayOperationCause cause) {
		if(cause == PlayOperationCause.COMPLETE){
			call.drop();
		}
	}

}
