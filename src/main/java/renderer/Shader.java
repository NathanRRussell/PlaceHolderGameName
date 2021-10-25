package renderer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;

public class Shader {

    private int shaderProgramID;

    private String vertexSource;
    private String fragmentSource;
    private String filepath;

    public Shader(String filepath) {
        this.filepath = filepath;
        try {
            String source = new String(Files.readAllBytes(Paths.get(filepath)));
            String[] splitString = source.split("(#type)( )+([a-zA-z]+)");

            int index = source.indexOf("#type") + 6;
            int endLine = source.indexOf("\r\n", index);
            String firstPattern = source.substring(index, endLine).trim();

            index = source.indexOf("#type", endLine) + 6;
            endLine = source.indexOf("\r\n", index);
            String secondPattern = source.substring(index, endLine).trim();

            if(firstPattern.equals("vertex")) {
                vertexSource = splitString[1];
            } else if(firstPattern.equals("fragment")) {
                fragmentSource = splitString[1];
            } else {
                throw new IOException("Unexpected token '"+firstPattern+"'");
            }

            if(secondPattern.equals("vertex")) {
                vertexSource = splitString[2];
            } else if(secondPattern.equals("fragment")) {
                fragmentSource = splitString[2];
            } else {
                throw new IOException("Unexpected token '"+secondPattern+"'");
            }
        } catch(IOException e) {
            e.printStackTrace();
            assert false : "Error: Could not open file for shader: '"+filepath+"'";
        }
    }

    public void compileAndLink() {
        // ==========================
        // Compile and Link Shaders
        // ==========================
        int vertexID, fragmentID;
        //Load and Compile Vertex Shader
        vertexID = glCreateShader(GL_VERTEX_SHADER);
        //Pass Shader Source to GPU
        glShaderSource(vertexID, vertexSource);
        glCompileShader(vertexID);
        //Check for Errors
        int success = glGetShaderi(vertexID, GL_COMPILE_STATUS);
        if(success == GL_FALSE) {
            int len = glGetShaderi(vertexID, GL_INFO_LOG_LENGTH);
            System.out.println("ERROR: '"+filepath+"'\n\tVertex shader compilation failed");
            System.out.println(glGetShaderInfoLog(vertexID, len));
            assert false : "";
        }

        //Load and Compile Vertex Shader
        fragmentID = glCreateShader(GL_FRAGMENT_SHADER);
        //Pass Shader Source to GPU
        glShaderSource(fragmentID, fragmentSource);
        glCompileShader(fragmentID);
        //Check for Errors
        success = glGetShaderi(fragmentID, GL_COMPILE_STATUS);
        if(success == GL_FALSE) {
            int len = glGetShaderi(fragmentID, GL_INFO_LOG_LENGTH);
            System.out.println("ERROR: '"+filepath+"'\n\tFragment shader compilation failed");
            System.out.println(glGetShaderInfoLog(fragmentID, len));
            assert false : "";
        }

        //Link Shaders
        shaderProgramID = glCreateProgram();
        glAttachShader(shaderProgramID, vertexID);
        glAttachShader(shaderProgramID,fragmentID);
        glLinkProgram(shaderProgramID);

        //Check for Errors
        success = glGetProgrami(shaderProgramID, GL_LINK_STATUS);
        if(success == GL_FALSE) {
            int len = glGetProgrami(shaderProgramID, GL_INFO_LOG_LENGTH);
            System.out.println("ERROR: '"+filepath+"'\n\tLinking shaders failed");
            System.out.println(glGetProgramInfoLog(shaderProgramID, len));
            assert false : "";
        }
    }

    public void use() {
        //Bind Shader Program
        glUseProgram(shaderProgramID);
    }

    public void detach() {
        glUseProgram(0);
    }
}
