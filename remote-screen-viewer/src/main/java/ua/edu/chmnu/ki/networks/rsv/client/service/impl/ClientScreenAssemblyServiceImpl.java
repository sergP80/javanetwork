package ua.edu.chmnu.ki.networks.rsv.client.service.impl;

import lombok.AllArgsConstructor;
import ua.edu.chmnu.ki.networks.rsv.client.assembly.FrameAssembler;
import ua.edu.chmnu.ki.networks.rsv.client.service.ClientScreenAssemblyService;
import ua.edu.chmnu.ki.networks.rsv.protocol.FrameChunk;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

@AllArgsConstructor
public class ClientScreenAssemblyServiceImpl implements ClientScreenAssemblyService {

    private final FrameAssembler assembler;

    @Override
    public BufferedImage tryToCompleteWith(FrameChunk chunk) throws IOException {
        byte[] frameBytes = assembler.accept(chunk);

        if (frameBytes != null) {
            return ImageIO.read(new ByteArrayInputStream(frameBytes));

        }
        return null;
    }
}
