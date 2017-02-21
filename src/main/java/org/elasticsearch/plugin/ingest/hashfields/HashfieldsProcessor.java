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

import org.elasticsearch.ingest.AbstractProcessor;
import org.elasticsearch.ingest.IngestDocument;
import org.elasticsearch.ingest.Processor;

import java.io.IOException;
import java.util.Map;
import java.util.List;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

import org.elasticsearch.common.hash.MessageDigests;
import static org.elasticsearch.ingest.ConfigurationUtils.readStringProperty;
import static org.elasticsearch.ingest.ConfigurationUtils.readList;

public class HashfieldsProcessor extends AbstractProcessor {

    public static final String TYPE = "hashfields";

    private final List<String> fields;
    private final String hashAlgorithm;
    private final String targetField;

    public HashfieldsProcessor(String tag, List<String> fields, String targetField, String hashAlgorithm) throws IOException {
        super(tag);
        this.fields = fields;
        this.targetField = targetField;
        this.hashAlgorithm = hashAlgorithm;
    }

    @Override
    public void execute(IngestDocument ingestDocument) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        for (String field : fields) {
            String content = ingestDocument.getFieldValue(field, String.class);
            bos.write(content.getBytes(StandardCharsets.UTF_8));
        }
        if ("sha1".equals(hashAlgorithm)) {
          ingestDocument.setFieldValue(targetField, MessageDigests.toHexString(MessageDigests.sha1().digest(bos.toByteArray())));
        } else if ("sha256".equals(hashAlgorithm)) {
          ingestDocument.setFieldValue(targetField, MessageDigests.toHexString(MessageDigests.sha256().digest(bos.toByteArray())));
        } else if ("md5".equals(hashAlgorithm)) {
          ingestDocument.setFieldValue(targetField, MessageDigests.toHexString(MessageDigests.md5().digest(bos.toByteArray())));
        } else {
          throw new IllegalArgumentException("invalid hash algorithm [" + hashAlgorithm + "]");
        }
    }

    @Override
    public String getType() {
        return TYPE;
    }

    public static final class Factory implements Processor.Factory {

        @Override
        public HashfieldsProcessor create(Map<String, Processor.Factory> factories, String tag, Map<String, Object> config) 
            throws Exception {
            List<String> fields = readList(TYPE, tag, config, "fields");
            String targetField = readStringProperty(TYPE, tag, config, "target_field");
            String hashAlgorithm = readStringProperty(TYPE, tag, config, "algorithm", "md5");

            return new HashfieldsProcessor(tag, fields, targetField, hashAlgorithm);
        }
    }
}
