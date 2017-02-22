# Elasticsearch hashfields Ingest Processor

Hashes a field or set of fields using md5, sha1 or sha256

## Usage


```
PUT _ingest/pipeline/hash-pipeline
{
  "description": "A pipeline to hash fields",
  "processors": [
    {
      "hashfields" : {
        "fields" : ["my_field"],
        "target_field" : "my_hashed_field",
        "algorithm" : "sha256"
      }
    }
  ]
}

PUT /my-index/my-type/1?pipeline_id=hash-pipeline
{
  "my_field" : "Some content"
}

GET /my-index/my-type/1
{
  "my_field" : "Some content"
  "my_hashed_field": "9c6609fc5111405ea3f5bb3d1f6b5a5efd19a0cec53d85893fd96d265439cd5b"
}
```

## Configuration

| Parameter     | Use                       |
| ------------- | ------------------------- |
| fields        | Array of fields to hash   |
| target_field  | Field to store the hashed value in |
| algorithm     | md5, sha1, or sha256 |

## Installation
Builds are provided for Elasticsearch 5.1.1 and above.  Versions of this plugin are tied to Elasticsearch versioning.
To install, you can download at `http://elastic-plugins.eskibars.com/artifacts/ingest-hashfields-<VERSION>.zip` and then install with

```bash
bin/plugin install file:///path/to/ingest-hashfields/build/distribution/ingest-hashfields-<VERSION>.zip
```

| Version | Location |
| ------- | -------- |
| 5.1.1 | http://elastic-plugins.eskibars.com/artifacts/ingest-hashfields-5.1.1.zip |
| 5.1.2 | http://elastic-plugins.eskibars.com/artifacts/ingest-hashfields-5.1.2.zip |
| 5.2.0 | http://elastic-plugins.eskibars.com/artifacts/ingest-hashfields-5.2.0.zip |
| 5.2.1 | http://elastic-plugins.eskibars.com/artifacts/ingest-hashfields-5.2.1.zip |

## Building

If you want to build yourself, you need to create a zip distribution first by running

```bash
gradle clean check
```

This will produce a zip file in `build/distributions`.

After building the zip file, you can install it like this

```bash
bin/plugin install file:///path/to/ingest-hashfields/build/distribution/ingest-hashfields-<VERSION>.zip
```

## Bugs & TODO

* More tests!
* Add separator configuration
* Support for non-string types
* More hash algorithms

