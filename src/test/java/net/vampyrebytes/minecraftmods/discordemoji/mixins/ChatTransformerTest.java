package net.vampyrebytes.minecraftmods.discordemoji.mixins;

import net.minecraft.text.LiteralText;
import net.vampyrebytes.minecraftmods.discordemoji.EmojiTransformer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.lang.reflect.Field;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ChatTransformerTest {

    @Mock
    private EmojiTransformer emojiTransformer;

    @Mock
    private ChatTransformer chatTransformer;

    @Test
    public void delegatesToEmojiTransformerCorrectly() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        Class<?> instanceHolder = Class.forName(EmojiTransformer.class.getCanonicalName() + "$InstanceHolder");
        Field instanceField = instanceHolder.getDeclaredField("INSTANCE");
        instanceField.setAccessible(true);
        instanceField.set(null, emojiTransformer);

        final String input = "Hello this is some text";

        when(chatTransformer.getString()).thenCallRealMethod();
        when(chatTransformer.of()).thenReturn(new LiteralText(input));

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        chatTransformer.getString();
        verify(emojiTransformer).transformEmojis(captor.capture());
        assertThat(captor.getValue()).isEqualTo(input);
    }
}
