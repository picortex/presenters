val googlePlacesJson = """
    {
      "address_components": [
        {
          "long_name": "Ubungo Terminal Road",
          "short_name": "Ubungo Terminal Rd",
          "types": ["route"]
        },
        {
          "long_name": "Dar es Salaam",
          "short_name": "DSM",
          "types": ["locality", "political"]
        },
        {
          "long_name": "Kinondoni",
          "short_name": "Kinondoni",
          "types": ["administrative_area_level_2", "political"]
        },
        {
          "long_name": "Dar es Salam",
          "short_name": "Dar es Salam",
          "types": ["administrative_area_level_1", "political"]
        },
        {
          "long_name": "Tanzania",
          "short_name": "TZ",
          "types": ["country", "political"]
        }
      ],
      "formatted_address": "Ubungo Terminal Rd, Dar es Salaam, Tanzania",
      "geometry": {
        "bounds": {
          "south": -6.7886158,
          "west": 39.2174148,
          "north": -6.7812578,
          "east": 39.2196886
        },
        "location": { "lat": -6.7849335, "lng": 39.2188214 },
        "location_type": "GEOMETRIC_CENTER",
        "viewport": {
          "south": -6.7886158,
          "west": 39.2172027197085,
          "north": -6.7812578,
          "east": 39.2199006802915
        }
      },
      "place_id": "ChIJmQ_KOr9OXBgRdRqCMfTWiLU",
      "types": ["route"],
      "description": "Ubungo Terminal Rd, Dar es Salaam, Tanzania"
    }
""".trimIndent()