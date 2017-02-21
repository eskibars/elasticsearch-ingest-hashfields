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

## Setup

In order to install this plugin, you need to create a zip distribution first by running

```bash
gradle clean check
```

This will produce a zip file in `build/distributions`.

After building the zip file, you can install it like this

```bash
bin/plugin install file:///path/to/ingest-hashfields/build/distribution/ingest-hashfields-0.0.1-SNAPSHOT.zip
```

## Bugs & TODO

* More tests!
* Add separator configuration
* More hash algorithms

