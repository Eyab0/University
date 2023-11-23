
gcc -c util.c -o util.o
ar rcs lib_myLibrary.a util.o

gcc -c parent.c -o parent.o 
gcc -c child.c -o child.o
gcc -c co-processor.c -o co-processor.o

gcc -o child child.o -L. lib_myLibrary.a
gcc -o co-processor co-processor.o -L. lib_myLibrary.a
gcc -o parent parent.o -L. lib_myLibrary.a -lglut -lGLU -lGL -lm


./parent $1 


rm -f *.o parent
rm -f *.o child
rm -f *.o co-processor
rm -f *.a lib_myLibrary

