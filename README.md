# XOPSManipulator

XOPSManipulator is a Java library that offers several functions to manipulate matters relating to X operations.

# About X operations

X operations (abbreviated to XOPS) is a classical FPS game first released in 2003 by a production team "team MITEI", which consists of two members, a programmer "nine-two" and a graphic designer "TENNKUU".

You could download the game from the [official website](https://hp.vector.co.jp/authors/VA022962/xops/).

# Overview

## Features

- Manipulation of BD1, PD1 and MIF files
- Conversion from the BD1 format to the Wavefront OBJ format
- Loading and writing from and to EXE files of XOPS (weapon data and character data)
- Support of the several file formats used in XMS (IDS, XGS and XCS files)
- XML input and output for XOPS-related data
- Parse and output of the source code of OpenXOPS (weapon data and character data)

## Samples

Samples are available in [XOPSManipulatorSamples](https://github.com/Dabasan/XOPSManipulatorSamples).

## Dependencies

- [javagl/Obj](https://github.com/javagl/Obj)
  This library is used to output Wavefront OBJ files.

## Install

### Maven project

Add the following lines to pom.xml in your project.

```xml
<dependency>
	<groupId>com.github.dabasan</groupId>
	<artifactId>xopsmanipulator</artifactId>
	<version>3.0.0</version>
</dependency>
```

### Java project

Set the required JARs to the build path.  

## Licenses

XOPSManipulator is released under the MIT license. See LICENSE for further information.

### javagl/Obj

> www.javagl.de - Obj
>
> Copyright (c) 2008-2015 Marco Hutter - http://www.javagl.de
>
> Permission is hereby granted, free of charge, to any person
> obtaining a copy of this software and associated documentation
> files (the "Software"), to deal in the Software without
> restriction, including without limitation the rights to use,
> copy, modify, merge, publish, distribute, sublicense, and/or sell
> copies of the Software, and to permit persons to whom the
> Software is furnished to do so, subject to the following
> conditions:
>
> The above copyright notice and this permission notice shall be
> included in all copies or substantial portions of the Software.
>
> THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
> EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
> OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
> NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
> HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
> WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
> FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
> OTHER DEALINGS IN THE SOFTWARE.

# External links

- [X operations WEB](https://hp.vector.co.jp/authors/VA022962/xops/)
  Official website of X operations
- [OpenXOPS公式サイト](http://openxops.net/)
  Official website of OpenXOPS
- [XOPSアドオン倉庫](https://sites.google.com/site/xopsaddonwarehouse/home)
  The author (Daba)'s website
- [Twitter](https://twitter.com/Daxie_tksm6)
  The author's Twitter account

