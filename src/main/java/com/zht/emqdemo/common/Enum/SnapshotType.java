package com.zht.emqdemo.common.Enum;

/**
 * @describe 统一响应码
 * @author zht
 * @date 2021/1/18
 */
public enum SnapshotType {

    CABINET("CABINET", "cabinet", "CABINET:", "柜子");

    /**
     * 快照类型英文大写
     */
    private String upperCase;

    /**
     * 快照类型英文小写
     */
    private String lowerCase;

    /**
     * 快照类型带前缀
     */
    private String prefix;

    /**
     * 描述
     */
    private String message;

    public String getUpperCase() {
        return upperCase;
    }

    public void setUpperCase(String upperCase) {
        this.upperCase = upperCase;
    }

    public String getLowerCase() {
        return lowerCase;
    }

    public void setLowerCase(String lowerCase) {
        this.lowerCase = lowerCase;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    SnapshotType(String upperCase, String lowerCase, String prefix, String message) {
        this.upperCase = upperCase;
        this.lowerCase = lowerCase;
        this.prefix = prefix;
        this.message = message;
    }

    /**
     * 根据快照大写获取快照枚举
     *
     * @param upperCase 快照类型英文大写
     * @return 快照枚举
     */
    public static SnapshotType getByUpperCase(String upperCase) {
        if (upperCase == null) {
            return null;
        }
        for (SnapshotType snapshotType : SnapshotType.values()) {
            if (snapshotType.getUpperCase().equals(upperCase)) {
                return snapshotType;
            }
        }
        return null;
    }
}
