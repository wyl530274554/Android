package com.melon.myapplication;

import com.melon.mylibrary.test.TestDemo;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.rule.PowerMockRule;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
@PowerMockIgnore({"org.mockito.*", "org.robolectric.*", "android.*"})
@PrepareForTest({TestDemo.class})
public class ExampleUnitTest {
    @Rule
    public PowerMockRule rule = new PowerMockRule();

    @Test
    public void testPrivateMethod() throws Exception {
        TestDemo mockClass = PowerMockito.spy(new TestDemo());

        mockClass.test1();

        Mockito.verify(mockClass,Mockito.times(1)).test1();
        PowerMockito.verifyPrivate(mockClass, Mockito.times(1)).invoke("test2");
    }
}