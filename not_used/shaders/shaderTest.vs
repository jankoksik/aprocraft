#version 120
attribute vec3 vertices;
attribute vec2 textures;

varying vec2 tex_coords;

uniform mat4 projection;

varying vec4 fragPos;
varying vec4 view;

void main(){
    tex_coords = textures;
    gl_Position = projection * vec4(vertices, 1);
    fragPos = gl_Vertex;
    view = gl_ModelViewMatrix * fragPos;
    gl_Position = ftransform();
}