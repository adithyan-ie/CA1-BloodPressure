def zap_started(zap, target):
    zap.urlopen(target)

    raw_request = (
        "POST /bp HTTP/1.1\r\n"
        f"Host: {target.replace('http://', '').replace('https://', '')}\r\n"
        "Content-Type: application/json\r\n"
        "Connection: close\r\n"
        "Content-Length: 33\r\n"
        "\r\n"
        '{"systolic":120,"diastolic":80}'
    )

    zap.core.send_request(raw_request)
