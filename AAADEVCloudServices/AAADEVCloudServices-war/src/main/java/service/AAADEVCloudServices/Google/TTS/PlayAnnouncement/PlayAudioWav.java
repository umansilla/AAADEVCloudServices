package service.AAADEVCloudServices.Google.TTS.PlayAnnouncement;

import java.net.URISyntaxException;

import service.AAADEVCloudServices.Google.TTS.MediaListener.MediaListenerAudio;
import service.AAADEVCloudServices.Util.TrafficInterfaceAddressRetrieverImpl;

import com.avaya.collaboration.call.Call;
import com.avaya.collaboration.call.media.MediaFactory;
import com.avaya.collaboration.call.media.MediaServerInclusion;
import com.avaya.collaboration.call.media.MediaService;
import com.avaya.collaboration.call.media.PlayItem;
import com.avaya.zephyr.platform.dal.api.ServiceUtil;

public class PlayAudioWav {
	private final Call call;
	public PlayAudioWav(final Call call){
		this.call = call;
	}
	
    public void playAnnouncement() throws URISyntaxException{
    	call.getCallPolicies().setMediaServerInclusion(MediaServerInclusion.AS_NEEDED);
    	String announcement = "PruebaDesdeWebService.wav";
    	
    	final TrafficInterfaceAddressRetrieverImpl addressRetriever = new TrafficInterfaceAddressRetrieverImpl();
		final String trafficInterfaceAddress = addressRetriever.getTrafficInterfaceAddress();
		final String myServiceName = ServiceUtil.getServiceDescriptor().getName();
		final StringBuilder sb = new StringBuilder();
		sb.append("http://").append(trafficInterfaceAddress)
				.append("/services/").append(myServiceName).append("/")
				.append(announcement);
		PlayItem playItem = MediaFactory.createPlayItem()
				.setInterruptible(false)
				.setIterateCount(1)
				.setSource(sb.toString());
		
		final MediaService mediaService = MediaFactory.createMediaService();
		final MediaListenerAudio mediaListenerAudio = new MediaListenerAudio(call);
		mediaService.play(call.getCallingParty(), playItem, mediaListenerAudio);
    }
}
