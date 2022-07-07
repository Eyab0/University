[audioIn,fs] = audioread("AnyAudio.wav");
sound(audioIn,fs)
windowLength = round(0.29*fs);
overlapLength = round(0.03*fs);
f0 =pitch(audioIn,fs,WindowLength=windowLength,OverlapLength=overlapLength,Range=[50,200],Method="PEF");
F0=mean(f0)
figure
subplot(2,1,1)

plot(audioIn)
ylabel("Amplitude")
xlabel("Time")
title("Sound")

subplot(2,1,2)
plot(f0,"*")
ylabel("Pitch (Hz)")
xlabel("Frame")
title("Pitch Contour")

if F0>=85 && F0<155
 	disp('It is a male voice!')
else if F0>=165 && F0<255
 	disp('It is a Female voice!')
else
	 disp('Error!')
 end

end