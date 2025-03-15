package uk.ac.ucl.model;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import uk.ac.ucl.model.StorageItems.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class ModelFactory
{
    private static Model model = null;
    final private static String path = "Data/data.json" ;

    private static JSONObject convertContentToJSON(Content content)
    {
        JSONObject obj = new JSONObject();
        obj.put("id", content.getId());
        obj.put("value", content.getValue());
        obj.put("type", content.getType().toString());
        return obj;
    }

    private static JSONObject convertNoteToJSON(Note note)
    {
        JSONObject obj = new JSONObject();
        obj.put("id", note.getId());
        obj.put("name", note.getName());
        obj.put("type", "Note") ;
        JSONArray contents = new JSONArray();
        for(Content content : note.getContents())
        {
            contents.add(convertContentToJSON(content));
        }
        obj.put("contents", contents);
        return obj;
    }

    private static JSONObject convertIndexToJSON(Index index)
    {
        JSONObject obj = new JSONObject();
        obj.put("id", index.getId());
        obj.put("name", index.getName());
        obj.put("type","Index");
        JSONArray subItems = new JSONArray();
        for(StorageItem items:index.getSubItems())
        {
            if(items instanceof Index) subItems.add(convertIndexToJSON((Index)items));
            else subItems.add(convertNoteToJSON((Note)items));
        }
        obj.put("subItems", subItems);
        return obj;
    }

    public static JSONObject convertToJSON()
    {
        Index mainIndex = model.getMainIndex();
        JSONObject obj = convertIndexToJSON(mainIndex);
        return obj;
    }

    public static void saveToFile()
    {
        JSONObject convertedData = convertToJSON() ;
        try (FileWriter file = new FileWriter(path))
        {
            file.write(convertedData.toString());
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

    public static TypeContent getEnumFromString(final String targetType)
    {
        TypeContent type;
        switch(targetType)
        {
            case "text":
                type = TypeContent.text;
                break;
            case "image":
                type = TypeContent.image;
                break;
            case "url":
                type = TypeContent.url;
                break;
            default:
                type = null;
        }
        return type;
    }

    private static Content getContentFromFile(JSONObject obj, Note parentNote)
    {
        TypeContent typeContent = getEnumFromString(obj.get("type").toString());
        String content = (String)obj.get("value") ;
        return new Content(typeContent,parentNote,content) ;
    }

    private static Note getNoteFromFile(JSONObject obj, Index parent)
    {
        Note note = new Note(parent, (String) obj.get("name")) ;
        for(Object object:(JSONArray)obj.get("contents"))
        {
            JSONObject content = (JSONObject)object;
            note.addContent(getContentFromFile(content,note)) ;
        }
        return note;
    }

    private static Index getIndexFromFile(JSONObject obj, Index parent)
    {
        Index index = new Index(parent, (String) obj.get("name")) ;
        for(Object object:(JSONArray)obj.get("subItems"))
        {
            JSONObject item = (JSONObject)object;
            if(item.get("type").equals("Index")) index.addStorageItem(getIndexFromFile(item,index)) ;
            else index.addStorageItem(getNoteFromFile(item,index)) ;
        }
        return index ;
    }

    private static Index getMainIndex(JSONObject JSONData)
    {
        StorageItem.numberOfItems = 0 ;
        StorageItem.Names = new HashMap<String,Integer>();
        Index mainIndex = getIndexFromFile(JSONData,null) ;
        mainIndex.setParent(mainIndex);
        return mainIndex;
    }

    private static Model getDataFromFile()
    {
        JSONParser parser = new JSONParser();
        try
        {
            JSONObject JSONData = (JSONObject)new JSONParser().parse(new FileReader(path));
            return new Model(getMainIndex(JSONData)) ;
        }
        catch(Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
        }
        return null;
    }

    private static void createModel()
    {
        model = getDataFromFile();
    }

    public static Model getModel()
    {
        if(model == null) createModel();
        return model;
    }
}
