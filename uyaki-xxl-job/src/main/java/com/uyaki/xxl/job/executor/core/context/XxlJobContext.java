package com.uyaki.xxl.job.executor.core.context;

/**
 * The type Xxl job context.
 *
 * @date 2020 /08/26
 */
public class XxlJobContext {
    /**
     * job id
     */
    private final long jobId;

    /**
     * job log filename
     */
    private final String jobLogFileName;

    /**
     * shard index
     */
    private final int shardIndex;

    /**
     * shard total
     */
    private final int shardTotal;


    /**
     * Instantiates a new Xxl job context.
     *
     * @param jobId          the job id
     * @param jobLogFileName the job log file name
     * @param shardIndex     the shard index
     * @param shardTotal     the shard total
     */
    public XxlJobContext(long jobId, String jobLogFileName, int shardIndex, int shardTotal) {
        this.jobId = jobId;
        this.jobLogFileName = jobLogFileName;
        this.shardIndex = shardIndex;
        this.shardTotal = shardTotal;
    }

    /**
     * Gets job id.
     *
     * @return the job id
     */
    public long getJobId() {
        return jobId;
    }

    /**
     * Gets job log file name.
     *
     * @return the job log file name
     */
    public String getJobLogFileName() {
        return jobLogFileName;
    }

    /**
     * Gets shard index.
     *
     * @return the shard index
     */
    public int getShardIndex() {
        return shardIndex;
    }

    /**
     * Gets shard total.
     *
     * @return the shard total
     */
    public int getShardTotal() {
        return shardTotal;
    }


    // ---------------------- tool ----------------------

    private static InheritableThreadLocal<XxlJobContext> contextHolder = new InheritableThreadLocal<>(); // support for child thread of job handler)

    /**
     * Set xxl job context.
     *
     * @param xxlJobContext the xxl job context
     */
    public static void setXxlJobContext(XxlJobContext xxlJobContext) {
        contextHolder.set(xxlJobContext);
    }

    /**
     * Get xxl job context xxl job context.
     *
     * @return the xxl job context
     */
    public static XxlJobContext getXxlJobContext() {
        return contextHolder.get();
    }
}
