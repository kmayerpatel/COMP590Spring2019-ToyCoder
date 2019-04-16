# COMP590 Spring 2019 Toy Coder

This repo contains a toy video encoder that you are free to play with in and/or use as the basis for a final project.

## Data Structures

The main data structures in the coder manipulate 2D arrays of integers in various ways. The base interface that all of
these structures implement is ```Block```. A block can report its geometry (width and height) and get/set values at a particular (x,y)
location. If understood as a block of pixel intensity data, (0,0) is the upper left corner and (width-1, height-1) is the lower right
corner. The value setter is "agile" in that it returns a reference to the resulting changed block so that additional methods on the
block can be chained. The implementations here are all mutable (i.e., change the underlying storage memory), but alternate immutable
implementations are possible.

A ```Macroblock``` is also an interface to a 2D array of integers (and thus is also a ```Block```), but is also understood to be
organized as a 2D array of fixed-size blocks. The geometry of a macroblock must be a multiple of the underlying blocks. A macroblock
can report the block geometry as blockWidth and blockHeight and can retrieve a specific block by a 2D "block index". The implementation
of ```Macroblock``` may or may not actually be storing a 2D array of blocks (in other words, it might actually have a 2D array of Block
structure or it might create those structures on the fly when asked for them). Like Block, the value setter is agile and the return
type of the setter is guaranteed to be a Macroblock (typically itself, but again, immutable implementations are possible).

A ```Frame``` is also an interface to a 2D array of integers (and thus has an is-a relationship to ```Block```), but is further
understood to be organized as a 2D array of fixed sized Macroblocks. Much like ```Macroblock```, ```Frame``` has methods for
retrieving the macroblock geometry as well as the block geoemetry within those macroblocks. In addition to being able to retrieve
a ```Macroblock``` by its macroblock index using the method ```mbTile```, a ```Frame``` is able to produce a ```Macroblock``` structure 
for any arbitrary macroblock sized region within the frame using the method ```mbExtract```. 

For each of the interfaces above, two types of implementations are provided: direct and indirect. A direct implementation encapsulates
the memory for the underlying values/structure directly. In otherwords, DirectBlock encapsulates a 2D arrray of integers, 
DirectMacroblock encapsulates a 2D array of Block, and Frame encapsulates a 2D array of Macroblock. The indirect implementations
do not encapsulate their own value memory but instead refer to values stored in a region of some other Block. The value setters 
of these indirect applications as implemented here as still mutable (i.e., will result in changes to the underlying Block storing the
values).

Extensions of the interfaces (and their corresponding direct and indirect implementations) are provided to model specific types
of values. These extension enforce value limits as appropriate and provide methods specific to the type of value being modelled. 
These include:
* ```ComponentBlock``, ```ComponentMacroblock```, and ```ComponentFrame```
  Intended to represent a particular 8-bit pixel component (i.e., luminance, chroma, red, green, blue, etc.). Values are retricted to
  the range [0-255]. ```ComponentBlock``` and ```ComponentMacroblock``` provide methods to create a residual block/macroblock from
  the difference with another component block/macroblock or to create the resulting component block/macroblock by adding a residual.
* ```ResidualBlock```, ```ResidualMacroblock```
  Residual block/macroblocks represent the signed difference between two component block/macroblocks. The corresponding residual
  frame structure is not defined at this time but only because I didn't need it for what I was doing. Residual block / macroblocks
  limit their values to [-255,255] and provide a DCT method to transform the values into a DCT block/macroblock. The DCT when
  applied to a residual macroblock results in applying the DCT to each underlying block area. Residual block / macroblocks also
  provide a method for calculating the mean absolute error (MAE) as the sum of their absolute values.
* ```DCTBlock```, ```DCTMacroblock```
  DCT block / macroblocks represent the coefficients of a 2D DCT as applied to a residual block / macroblock. Values are not 
  limited to any particular range. An idct() method is provide to invert the DCT to recover a residual block/macroblocks. Additional
  methods are provided to quantize and dequantize given a qfactor. The results of these methods are new DCT block / macroblocks
  with the resulting values (i.e., the original DCT block / macroblock remains unchanged). 
  
Like the base interfaces, both direct and indirect implementations of these extended interfaces is provided and the result
of the value setter will be the same type as the original structure.

