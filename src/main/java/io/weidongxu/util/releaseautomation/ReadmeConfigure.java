package io.weidongxu.util.releaseautomation;

import com.azure.core.exception.HttpResponseException;
import com.azure.core.http.HttpMethod;
import com.azure.core.http.HttpPipeline;
import com.azure.core.http.HttpRequest;
import com.azure.core.http.HttpResponse;

import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReadmeConfigure {

    public static class TagConfigure {
        private final String tagName;
        private final List<String> inputFiles = new ArrayList<>();

        public TagConfigure(String tagName) {
            this.tagName = tagName;
        }

        public String getTagName() {
            return tagName;
        }

        public List<String> getInputFiles() {
            return inputFiles;
        }
    }

    private String defaultTag = null;
    private final List<TagConfigure> tagConfigures = new ArrayList<>();

    public String getDefaultTag() {
        return defaultTag;
    }

    public List<TagConfigure> getTagConfigures() {
        return tagConfigures;
    }

    public static ReadmeConfigure parseReadme(HttpPipeline httpPipeline, URL readmeUrl) {
        HttpRequest request = new HttpRequest(HttpMethod.GET, readmeUrl);
        HttpResponse response = httpPipeline.send(request).block();
        if (response.getStatusCode() == 200) {
            String readme = response.getBodyAsString().block();
            response.close();

            ReadmeConfigure configure = new ReadmeConfigure();
            String[] lines = readme.split("\r?\n");

            ReadmeConfigure.TagConfigure currentTag = null;
            boolean inputFiles = false;
            for (String line : lines) {
                line = line.trim();
                if (line.equals("```")) {
                    if (currentTag != null) {
                        configure.getTagConfigures().add(currentTag);
                    }
                    currentTag = null;
                    inputFiles = false;
                } else if (line.startsWith("```")) {
                    String compressedLine = line.replaceAll(" ", "");
                    if (compressedLine.startsWith("```yaml$(tag)=='")) {
                        Matcher matcher = Pattern.compile("^yaml\\$\\(tag\\)=='(.*)'$").matcher(compressedLine);
                        if (matcher.find()) {
                            String tag = matcher.group(1);
                            currentTag = new TagConfigure(tag);
                        }
                    }
                } else if (currentTag != null && line.equals("input-file:")) {
                    inputFiles = true;
                } else if (currentTag != null && inputFiles) {
                    if (line.startsWith("-")) {
                        currentTag.getInputFiles().add(line.substring(1).trim());
                    } else {
                        inputFiles = false;
                    }
                } else if (currentTag == null && line.startsWith("tag:")) {
                    configure.defaultTag = line.substring(4).trim();
                }
            }

            configure.getTagConfigures().sort((o1, o2) -> o2.getTagName().compareTo(o1.getTagName()));

            return configure;
        } else {
            response.close();
            throw new HttpResponseException(response);
        }
    }

    public void print(PrintStream out, int recent) {
        out.println("default tag: " + getDefaultTag());
        out.println("recent " + recent + " tags:");
        for (int i = 0; i < Math.min(recent, getTagConfigures().size()); ++i) {
            TagConfigure tag = getTagConfigures().get(i);
            out.println("tag: " + tag.getTagName());
            for (String inputFile : tag.getInputFiles()) {
                out.println("  " + inputFile);
            }
        }
    }
}
