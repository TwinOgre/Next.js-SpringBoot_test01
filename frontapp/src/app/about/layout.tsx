import Link from "next/link";
export default function AboutLayout({
    children,
  }: Readonly<{
    children: React.ReactNode;
  }>) {
    return (
        <>
        <h1>소개입니다.✔</h1> <span> | </span> <Link href="/about/me">내 소개 페이지</Link>
        {children}
        </>
    )
  }