package com.robot.entity.tl;

import lombok.Data;

@Data
public class Perception {
    private InputText inputText;
    private InputImage inputImage;
    private SelfInfo selfInfo;
}
