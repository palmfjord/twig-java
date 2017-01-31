package org.twigjava.syntax.parser.node.type;

import org.junit.Test;
import org.twigjava.compiler.ClassCompiler;

import java.util.ArrayList;
import java.util.HashMap;

import static org.mockito.Mockito.*;

public class TextTests {
    @Test
    public void canCompile() {
        ClassCompiler compilerStub = mock(ClassCompiler.class);
        HashMap<String, Object> data = new HashMap<>();
        data.put("data", "foo");
        Text text = new Text(new ArrayList<>(), data, 1, "");
        text.compile(compilerStub);

        verify(compilerStub).write("output = output.concat(");
        verify(compilerStub).writeString("foo");
        verify(compilerStub).writeRaw(");\n");
    }
}