package com.k2ka.library.vrtmp.io.packets;

import com.k2ka.library.vrtmp.utils.Util;
import com.k2ka.library.vrtmp.io.ChunkStreamInfo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * (Window) Acknowledgement
 * 
 * The client or the server sends the acknowledgment to the peer after
 * receiving bytes equal to the window size. The window size is the
 * maximum number of bytes that the sender sends without receiving
 * acknowledgment from the receiver. The server sends the window size to
 * the client after application connects. This message specifies the
 * sequence number, which is the number of the bytes received so far.
 * 
 * @author k2ka
 */
public class Acknowledgement extends RtmpPacket {

    private int sequenceNumber;

    public Acknowledgement(RtmpHeader header) {
        super(header);
    }

    public Acknowledgement(int numBytesReadThusFar) {
        super(new RtmpHeader(RtmpHeader.ChunkType.TYPE_0_FULL, ChunkStreamInfo.CONTROL_CHANNEL, RtmpHeader.MessageType.ACKNOWLEDGEMENT));
        this.sequenceNumber = numBytesReadThusFar;
    }

    public int getAcknowledgementWindowSize() {
        return sequenceNumber;
    }

    /** @return the sequence number, which is the number of the bytes received so far */
    public int getSequenceNumber() {
        return sequenceNumber;
    }

    /** Sets the sequence number, which is the number of the bytes received so far */
    public void setSequenceNumber(int numBytesRead) {
        this.sequenceNumber = numBytesRead;
    }

    @Override
    public void readBody(InputStream in) throws IOException {
        sequenceNumber = Util.readUnsignedInt32(in);
    }

    @Override
    protected void writeBody(OutputStream out) throws IOException {
        Util.writeUnsignedInt32(out, sequenceNumber);
    }

    @Override
    public String toString() {
        return "RTMP Acknowledgment (sequence number: " + sequenceNumber + ")";
    }
}
