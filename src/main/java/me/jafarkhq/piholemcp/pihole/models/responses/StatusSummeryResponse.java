package me.jafarkhq.piholemcp.pihole.models.responses;

public record StatusSummeryResponse(
        Queries queries,
        Clients clients,
        Gravity gravity,
        double took) {

    public record Types(
            int A,
            int AAAA,
            int ANY,
            int SRV,
            int SOA,
            int PTR,
            int TXT,
            int NAPTR,
            int MX,
            int DS,
            int RRSIG,
            int DNSKEY,
            int NS,
            int SVCB,
            int HTTPS,
            int OTHER) {
    }

    public record Status(
            int UNKNOWN, int GRAVITY, int FORWARDED, int CACHE, int REGEX, int DENYLIST,
            int EXTERNALBLOCKEDIP, int EXTERNALBLOCKEDNULL, int EXTERNALBLOCKEDNXRA, int GRAVITYCNAME,
            int REGEXCNAME, int DENYLISTCNAME, int RETRIED, int RETRIEDDNSSEC, int INPROGRESS, int DBBUSY,
            int SPECIALDOMAIN, int CACHESTALE, int EXTERNALBLOCKEDEDE15) {
    }

    public record Replies(
            int UNKNOWN, int NODATA, int NXDOMAIN, int CNAME, int IP, int DOMAIN, int RRNAME,
            int SERVFAIL, int REFUSED, int NOTIMP, int OTHER, int DNSSEC, int NONE, int BLOB) {
    }

    public record Queries(
            int total, int blocked, double percentBlocked, int uniqueDomains, int forwarded, int cached,
            double frequency, Types types, Status status, Replies replies) {
    }

    public record Clients(
            int active,
            int total) {
    }

    public record Gravity(
            int domainsBeingBlocked,
            int lastUpdate) {
    }

}

