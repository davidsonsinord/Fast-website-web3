package com.davidson.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PathIsNotDirectoryException extends Exception {

    private static final Logger log = LoggerFactory.getLogger(PathIsNotDirectoryException.class);

    public PathIsNotDirectoryException() {
        log.error("path is not directory");
    }
}
