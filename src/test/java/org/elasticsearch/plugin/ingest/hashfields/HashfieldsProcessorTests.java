/*
 * Copyright [2016] [Shane Connelly]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.elasticsearch.plugin.ingest.hashfields;

import com.carrotsearch.randomizedtesting.generators.RandomStrings;

import org.elasticsearch.ingest.IngestDocument;
import org.elasticsearch.ingest.RandomDocumentPicks;
import org.elasticsearch.test.ESTestCase;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;

public class HashfieldsProcessorTests extends ESTestCase {

    public void testThatProcessorWorks() throws Exception {
        Map<String, Object> document = new HashMap<>();
        document.put("source_field", "abc");
        IngestDocument ingestDocument = RandomDocumentPicks.randomIngestDocument(random(), document);

        List<String> s = new ArrayList<String>();
        s.add("source_field");
        String str = RandomStrings.randomAsciiLettersOfLength(random(), 10);
        HashfieldsProcessor processor = new HashfieldsProcessor(str, s, "target_field", "md5");
        processor.execute(ingestDocument);
        Map<String, Object> data = ingestDocument.getSourceAndMetadata();

        assertThat(data, hasKey("target_field"));
        assertThat(data.get("target_field"), is("900150983cd24fb0d6963f7d28e17f72"));
        // TODO add fancy assertions here
    }
}

