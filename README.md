# XOPSManipulator

XOPSManipulatorはX operationsに関する事柄を操作するためのJavaライブラリです。

# X operationsについて

X operations(略してXOPS)は2003年に初リリースされたFPSゲームで、プログラマのnine-two氏とグラフィックデザイナーのTENNKUU氏からなる制作チームteam MITEIによって制作されました。

[公式サイト](https://hp.vector.co.jp/authors/VA022962/xops/)からゲームをダウンロードすることができます。

# 概要

## 機能

### BD1モジュール

- BD1データの基本操作(平行移動・回転・拡大縮小など)
- BD1→OBJへの変換
- ブロックの生成

### PD1モジュール

- PD1データの基本操作(平行移動・回転・拡大縮小など)

### MIFモジュール

- MIFファイルからミッション情報を取得
- 複数のMIFファイルからミッション情報のリストを作成

### Propertiesモジュール

- XOPSの実行ファイルから武器情報・キャラクター情報を取得
- XMSで使用されるファイル形式から情報を取得(IDS, XGS, XCSファイル)
- 武器情報・キャラクター情報をOpenXOPSのソースコード形式で出力
- 武器情報・キャラクター情報をXML形式で出力

## インストール

### Mavenプロジェクト

#### BD1モジュール

```xml
<dependency>
	<groupId>com.github.dabasan</groupId>
	<artifactId>xopsmanipulator-bd1</artifactId>
	<version>8.0.0</version>
</dependency>
```

#### PD1モジュール

```xml
<dependency>
	<groupId>com.github.dabasan</groupId>
	<artifactId>xopsmanipulator-pd1</artifactId>
	<version>8.0.0</version>
</dependency>
```

#### MIFモジュール

```xml
<dependency>
	<groupId>com.github.dabasan</groupId>
	<artifactId>xopsmanipulator-mif</artifactId>
	<version>8.0.0</version>
</dependency>
```

#### Propertiesモジュール(武器情報の操作など)

```xml
<dependency>
	<groupId>com.github.dabasan</groupId>
	<artifactId>xopsmanipulator-properties</artifactId>
	<version>8.0.0</version>
</dependency>
```

## サンプル

サンプルは[XOPSManipulatorSamples](https://github.com/Dabasan/XOPSManipulatorSamples)を参照してください。

## 依存

- [Obj](https://github.com/javagl/Obj)
  Wavefront OBJファイルを出力するために使用しています。

## ライセンス

XOPSManipulatorはMITライセンスの下に公開されています。

### 依存

- [Obj](https://github.com/javagl/Obj/blob/master/LICENSE.txt)

# 外部リンク

- [X operations WEB](https://hp.vector.co.jp/authors/VA022962/xops/)
  X operationsの公式サイト
- [OpenXOPS公式サイト](http://openxops.net/)
  OpenXOPSの公式サイト
- [XOPSアドオン倉庫](https://sites.google.com/site/xopsaddonwarehouse/home)
  作者(駄場)のウェブサイト

