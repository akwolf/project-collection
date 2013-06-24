package com.justsy.android.manifest;

import android.content.res.AXmlResourceParser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class AXMLPrinter
{
  private static final float[] RADIX_MULTS = { 
    0.0039063F, 3.051758E-005F, 1.192093E-007F, 4.656613E-010F };

  private static final String[] DIMENSION_UNITS = { 
    "px", "dip", "sp", "pt", "in", "mm", "", "" };

  private static final String[] FRACTION_UNITS = { 
    "%", "%p", "", "", "", "", "", "" };
  static StringBuffer sb=null;
  public static InputStream startXMLPrint(InputStream stream)
  {
    try {
    	sb = new StringBuffer();
      AXmlResourceParser parser = new AXmlResourceParser();
      parser.open(stream);
      StringBuilder indent = new StringBuilder(10);
      //String indentStep = "\t";
      while (true) {
        int type = parser.next();
        if (type == 1) {
          break;
        }
        switch (type)
        {
        case 0:
          log("<?xml version=\"1.0\" encoding=\"utf-8\"?>", new Object[0]);
          break;
        case 2:
          log("%s<%s%s", new Object[] { indent, 
            getNamespacePrefix(parser.getPrefix()), parser.getName() });
          indent.append("\t");

          int namespaceCountBefore = parser.getNamespaceCount(parser.getDepth() - 1);
          int namespaceCount = parser.getNamespaceCount(parser.getDepth());
          for (int i = namespaceCountBefore; i != namespaceCount; i++) {
            log("%sxmlns:%s=\"%s\"", new Object[] { 
              indent, 
              parser.getNamespacePrefix(i), 
              parser.getNamespaceUri(i) });
          }

          for (int i = 0; i != parser.getAttributeCount(); i++) {
            log("%s%s%s=\"%s\"", new Object[] { indent, 
              getNamespacePrefix(parser.getAttributePrefix(i)), 
              parser.getAttributeName(i), 
              getAttributeValue(parser, i) });
          }
          log("%s>", new Object[] { indent });
          break;
        case 3:
          indent.setLength(indent.length() - "\t".length());
          log("%s</%s%s>", new Object[] { indent, 
            getNamespacePrefix(parser.getPrefix()), 
            parser.getName() });
          break;
        case 4:
          log("%s%s", new Object[] { indent, parser.getText() });
        case 1:
        }
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    ByteArrayInputStream stream2 = new ByteArrayInputStream(sb.toString().getBytes());
	return stream2;
  }

  private static String getNamespacePrefix(String prefix) {
    if ((prefix == null) || (prefix.length() == 0)) {
      return "";
    }
    return prefix + ":";
  }

  private static String getAttributeValue(AXmlResourceParser parser, int index) {
    int type = parser.getAttributeValueType(index);
    int data = parser.getAttributeValueData(index);
    if (type == 3) {
      return parser.getAttributeValue(index);
    }
    if (type == 2) {
      return String.format("?%s%08X", new Object[] { getPackage(data), Integer.valueOf(data) });
    }
    if (type == 1) {
      return String.format("@%s%08X", new Object[] { getPackage(data), Integer.valueOf(data) });
    }
    if (type == 4) {
      return String.valueOf(Float.intBitsToFloat(data));
    }
    if (type == 17) {
      return String.format("0x%08X", new Object[] { Integer.valueOf(data) });
    }
    if (type == 18) {
      return data != 0 ? "true" : "false";
    }
    if (type == 5) {
      return Float.toString(complexToFloat(data)) + 
        DIMENSION_UNITS[(data & 0xF)];
    }
    if (type == 6) {
      return Float.toString(complexToFloat(data)) + 
        FRACTION_UNITS[(data & 0xF)];
    }
    if ((type >= 28) && (type <= 31)) {
      return String.format("#%08X", new Object[] { Integer.valueOf(data) });
    }
    if ((type >= 16) && (type <= 31)) {
      return String.valueOf(data);
    }
    return String.format("<0x%X, type 0x%02X>", new Object[] { Integer.valueOf(data), Integer.valueOf(type) });
  }

  private static String getPackage(int id) {
    if (id >>> 24 == 1) {
      return "android:";
    }
    return "";
  }

  public static void log(String format, Object[] arguments) {
	 String a = String.format(format, arguments);
	 sb.append(a+"\n");
  }

  public static float complexToFloat(int complex)
  {
    return (complex & 0xFFFFFF00) * RADIX_MULTS[(complex >> 4 & 0x3)];
  }
}