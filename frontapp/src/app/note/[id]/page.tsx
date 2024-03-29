export default function Note({ params }: { params: { id: string } }) {
    return (
      <div>
        ✨노트 No. {params.id}✨
      </div>
    );
  }