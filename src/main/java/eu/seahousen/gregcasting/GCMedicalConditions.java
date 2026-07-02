package eu.seahousen.gregcasting;

import at.petrak.hexcasting.common.lib.HexAttributes;
import com.gregtechceu.gtceu.api.data.medicalcondition.MedicalCondition;
import com.gregtechceu.gtceu.api.data.medicalcondition.Symptom;
import com.gregtechceu.gtceu.api.registry.GTRegistries;

import java.util.UUID;

public class GCMedicalConditions {
    public static Symptom SMALLER_GRID = Symptom.ofAttributeModifier(
            "smaller_grid",
            5,
            0.0F,
            1.0F,
            1F / 6,
            HexAttributes.GRID_ZOOM,
            UUID.fromString("38a50328-df50-45be-be82-1dd80562afd6") // chosen by fair dice roll.
                                                                          // guaranteed to be random.
    );

    public static Symptom SMALLER_AMBIT = Symptom.ofAttributeModifier(
            "smaller_ambit",
            5,
            0.0F,
            1.0F,
            1F / 6,
            HexAttributes.AMBIT_RADIUS,
            UUID.fromString("46f3a295-f78c-41d9-b825-81f08d732af6")
    );

    public static Symptom MORE_MEDIA = Symptom.ofAttributeModifier(
            "more_media",
            6,
            0.0F,
            1.0F,
            - 1F / 6,
            HexAttributes.MEDIA_CONSUMPTION_MODIFIER,
            UUID.fromString("a537b4c1-9168-4ee9-9040-a49293872865")
    );

    public static Symptom NO_MORE_GSENT_FOR_YOU = Symptom.ofAttributeModifier(
            "no_gsent",
            1,
            0.0F,
            1.0F,
            1F,
            HexAttributes.SENTINEL_RADIUS,
            UUID.fromString("5afe73a2-70a3-4d94-9bd6-4edfdd156e52")
    );

    public static MedicalCondition XENOMEDIA = new MedicalCondition(
            GregCasting.id("xmedia"),
            0x6296c4,
            300,
            MedicalCondition.IdleProgressionType.NONE,
            0.0F,
            false,
            new Symptom.ConfiguredSymptom(SMALLER_GRID, 5, 50, 300),
            new Symptom.ConfiguredSymptom(SMALLER_AMBIT, 5, 100, 300),
            new Symptom.ConfiguredSymptom(MORE_MEDIA, 6, 150, 300),
            new Symptom.ConfiguredSymptom(NO_MORE_GSENT_FOR_YOU, 1, 290, 300)
    );

    public static void init() {
        GTRegistries.MEDICAL_CONDITIONS.register(XENOMEDIA.id, XENOMEDIA);
    }
}
