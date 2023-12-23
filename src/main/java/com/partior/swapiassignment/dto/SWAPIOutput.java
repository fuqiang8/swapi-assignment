package com.partior.swapiassignment.dto;

import java.util.List;

public record SWAPIOutput<T> (List<T> results) {
}
