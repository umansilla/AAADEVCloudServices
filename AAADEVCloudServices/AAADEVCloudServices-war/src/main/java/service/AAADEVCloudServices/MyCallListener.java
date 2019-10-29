package service.AAADEVCloudServices;

import java.net.URISyntaxException;

import service.AAADEVCloudServices.Google.TTS.PlayAnnouncement.PlayAudioWav;

import com.avaya.collaboration.call.Call;
import com.avaya.collaboration.call.CallListenerAbstract;
import com.avaya.collaboration.call.TheCallListener;
import com.avaya.collaboration.util.logger.Logger;

/*
 * This class is needed if an application with call features is written.
 * If you have an application which is doing only HTTP related operations, remove this class from the project.
 * 
 * For HTTP only application, also remove the sip.xml from src/main/java/webapp/WEB-INF and blank out details from
 * CARRule.xml. Look at the files for more details.
 * 
 */
@TheCallListener
public class MyCallListener extends CallListenerAbstract 
{
    private static Logger logger = Logger.getLogger(MyCallListener.class);
   
	public MyCallListener() 
	{
	}
	
	@Override
	public final void callIntercepted(final Call call) 
	{
		if (call.isCalledPhase()){
			logger.info("callIntercepted");
			PlayAudioWav audio = new PlayAudioWav(call);
			try {
				audio.playAnnouncement();
			} catch (URISyntaxException e) {
				logger.error("Error al reproducir el audio " + e.toString());
			}
		}else{
			call.allow();
		}
	}
}
