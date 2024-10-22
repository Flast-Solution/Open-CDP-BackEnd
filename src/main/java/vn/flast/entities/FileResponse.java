package vn.flast.entities;

import lombok.Builder;

public record FileResponse(
    Integer id,
    String uid,
    String name,
    String status,
    String url
) {
    @Override
    public String status() {
        return status == null ? "done" : status;
    }

    @Builder(builderMethodName = "fileBuilder")
    public FileResponse {}
}
