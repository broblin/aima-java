package aima.gui.support.code;

import aima.extra.instrument.search.TreeSearchCmdInstr;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;


/**
 * @author Ciaran O'Reilly
 */
public class CodeReader {
    public static final String CODE_TYPE_PREFIX   = "@CODE:";
    public static final String CODE_MARKER_PREFIX = "@";

    // Test Main
    public static void main(String[] args) {
        read("tree-search.code", TreeSearchCmdInstr.CMDS);
    }

    public static List<CodeRepresentation> read(String codeFileName, Set<String> expectedCmds) {
        final List<CodeRepresentation> result = new ArrayList<>();

        final StringBuilder codeTypeName = new StringBuilder();
        final StringBuilder source       = new StringBuilder();
        final Map<String, List<SourceIndex>> codeCommands = new LinkedHashMap<>();
        try (Stream<String> lines = Files.lines(Paths.get(CodeReader.class.getResource(codeFileName).toURI()))) {
            lines.forEach(line -> {
                if (line.startsWith(CODE_TYPE_PREFIX)) {
                    // If we have already processed a code representation
                    if (codeTypeName.length() > 0) {
                        result.add(new CodeRepresentation(codeTypeName.toString(), source.toString(), convert(codeCommands)));
                    }

                    codeTypeName.setLength(0);
                    source.setLength(0);
                    codeTypeName.append(line.substring(CODE_TYPE_PREFIX.length()));
                }
                else {
                    process(line, source, codeCommands);
                }
            });
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
        catch (URISyntaxException urie) {
            urie.printStackTrace();
        }
        // Ensure the last code representation is included as well
        if (codeTypeName.length() > 0) {
            CodeRepresentation cr = new CodeRepresentation(codeTypeName.toString(), source.toString(), convert(codeCommands));
            if (!cr.commandIdToCommand.keySet().equals(expectedCmds)) {
                throw new IllegalArgumentException("Code Representation is missing command ids :\n"+cr.commandIdToCommand.keySet()+"\n"+expectedCmds);
            }
            result.add(cr);
        }

        return result;
    }

    private static void process(String line, StringBuilder source, Map<String, List<SourceIndex>> codeCommands) {
        int s = 0;
        while (s < line.length()) {
            int m = line.indexOf(CODE_MARKER_PREFIX, s);
            if (m == -1) {
                source.append(line.substring(s, line.length()));
                s = line.length();
            }
            else {
                if (s < m) {
                    source.append(line.substring(s, m));
                }
                s = processMarker(m, line, source, codeCommands);
            }
        }
        source.append("\n");
    }

    private static int processMarker(int s1, String line, StringBuilder source, Map<String, List<SourceIndex>> codeCommands) {
        int e1 = line.indexOf(CODE_MARKER_PREFIX, s1+1);
        int s2 = line.indexOf(CODE_MARKER_PREFIX, e1+1);
        int e2 = line.indexOf(CODE_MARKER_PREFIX, s2+1);

        if (e1 == -1 || s2 == -1 || e2 == -1) {
            throw new IllegalArgumentException("Code line has invalid code command marker starting at "+s1+" :\n"+line);
        }

        String c1 = line.substring(s1+1, e1);
        String c2 = line.substring(s2+1, e2);

        if (!c1.equals(c2)) {
            throw new IllegalArgumentException("Code marker start and begin tags do not match up ("+c1+", "+c2+") :\n"+line);
        }

        List<SourceIndex> sis = codeCommands.get(c1);
        if (sis == null) {
            sis = new ArrayList<>();
            codeCommands.put(c1, sis);
        }

        String sourceFragment = line.substring(e1+1, s2);
        sis.add(new SourceIndex(source.length() + 1, source.length() + sourceFragment.length()));
        source.append(sourceFragment);

        return e2+1;
    }

    private static List<CodeCommand> convert(Map<String, List<SourceIndex>> commandIdsToSourceIndexes) {
        final List<CodeCommand> result = new ArrayList<>();

        commandIdsToSourceIndexes.forEach((k, v) -> result.add(new CodeCommand(k, v)));

        return result;
    }
}